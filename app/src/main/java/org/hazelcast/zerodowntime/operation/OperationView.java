package org.hazelcast.zerodowntime.operation;

public class OperationView {

    private final String hostname;
    private final String version;

    public OperationView(String hostname, String version) {
        this.hostname = hostname;
        this.version = version;
    }

    @SuppressWarnings("unused")
    public String getHostname() {
        return hostname;
    }

    @SuppressWarnings("unused")
    public String getVersion() {
        return version;
    }
}