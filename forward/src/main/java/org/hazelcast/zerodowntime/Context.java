package org.hazelcast.zerodowntime;

import com.hazelcast.function.FunctionEx;
import com.hazelcast.jet.core.Processor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.Serializable;

class Context implements Serializable {

    private final transient HikariDataSource dataSource;

    public Context(String user, String password, String url) {
        var config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public static FunctionEx<Processor.Context, Context> create(String user, String password, String url) {
        return context -> new Context(user, password, url);
    }
}