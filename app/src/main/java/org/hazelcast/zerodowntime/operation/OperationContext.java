package org.hazelcast.zerodowntime.operation;

import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OperationContext {

    @Value("${info.app.version}")
    private String version;

    private String hostname;

    public String getVersion() {
        return version;
    }

    public String getHostname() {
        if (hostname == null) {
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                throw new IllegalStateException(e);
            }
        }
        return hostname;
    }
}
