{{- define "database.deployment" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Values.color }}
  namespace: zerodowntime
  labels:
    color: {{ .Values.color }}
spec:
  replicas: 1
  selector:
    matchLabels:
      color: {{ .Values.color }}
  template:
    metadata:
      labels:
        color: {{ .Values.color }}
    spec:
      containers:
        - name: mysql
          image: mysql:8.0
          args:
            - mysqld
            - --lower_case_table_names=1
            - --log-bin=ON
            - --binlog_format=ROW
            - --server-id=1
            - --default-authentication-plugin=mysql_native_password
          env:
            - name: MYSQL_DATABASE
              valueFrom:
                configMapKeyRef:
                  name: parameters
                  key: mysql_database
            - name: MYSQL_ROOT_PASSWORD
              valueFrom:
                configMapKeyRef:
                  name: parameters
                  key: mysql_password
          ports:
            - containerPort: 3306
          volumeMounts:
            - mountPath: /docker-entrypoint-initdb.d
              name: initscripts
      volumes:
        - name: initscripts
          configMap:
            name: {{ .Values.color }}-scripts
{{- end }}