package org.hazelcast.zerodowntime;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.JetService;
import com.hazelcast.jet.cdc.ChangeRecord;
import com.hazelcast.jet.cdc.RecordPart;
import com.hazelcast.jet.cdc.mysql.MySqlCdcSources;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sink;
import com.hazelcast.jet.pipeline.SinkBuilder;
import com.hazelcast.jet.pipeline.StreamSource;

import java.util.Map;

public class Forward {

    private static final String DB_SERVER_NAME = "blue";
    private static final String DB_SCHEMA = "shop";
    private static final String DB_NAMESPACED_TABLE = DB_SCHEMA + ".CART_LINE";

    private final JetService jet;

    public Forward(JetService jet) {
        this.jet = jet;
    }

    public static void main(String[] args) {
        new Forward(Hazelcast.bootstrappedInstance().getJet()).run();
    }

    private void run() {
        jet.newJob(pipeline(System.getenv()), jobConfig());
    }

    private Pipeline pipeline(Map<String, String> env) {
        var pipeline = Pipeline.create();
        pipeline.readFrom(blue(env))
                .withIngestionTimestamps()
                .map(ChangeRecord::value)
                .map(RecordPart::toMap)
                .map(CallParameters::toCallParameters)
                .writeTo(green(env));
        return pipeline;
    }

    private JobConfig jobConfig() {
        var jobConfig = new JobConfig();
        jobConfig.setName("forward");
        return jobConfig;
    }

    private StreamSource<ChangeRecord> blue(Map<String, String> env) {
        return MySqlCdcSources.mysql("mysql-connector")
                .setDatabaseAddress(env.getOrDefault("MYSQL_SOURCE_HOST", "localhost"))
                .setDatabasePort(Integer.parseInt(env.getOrDefault("MYSQL_PORT", "3306")))
                .setDatabaseUser(env.getOrDefault("MYSQL_SOURCE_USER", "root"))
                .setDatabasePassword(env.getOrDefault("MYSQL_SOURCE_PASSWORD", "root"))
                .setClusterName(DB_SERVER_NAME)
                .setDatabaseWhitelist(DB_SCHEMA)
                .setTableWhitelist(DB_NAMESPACED_TABLE)
                .build();
    }

    private Sink<CallParameters> green(Map<String, String> env) {
        var url = env.getOrDefault("MYSQL_TARGET_URL", "jdbc:mysql://localhost:3306/shop");
        var user = env.getOrDefault("MYSQL_TARGET_USER", "root");
        var password = env.getOrDefault("MYSQL_TARGET_PASSWORD", "root");
        return SinkBuilder.sinkBuilder("database", Context.create(user, password, url))
                .receiveFn(new InsertCartLine())
                .build();
    }
}