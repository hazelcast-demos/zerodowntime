{{- define "database.service" -}}
apiVersion: v1
kind: Service
metadata:
  namespace: zerodowntime
  name: {{ .Values.color }}
spec:
  selector:
    color: {{ .Values.color }}
  ports:
    - port: 3306
{{- end }}