package org.hazelcast.zerodowntime.cart;

import org.hazelcast.zerodowntime.operation.OperationContext;

public class CartView {

    private final int itemCount;
    private final String hostname;
    private final String version;

    public CartView(int itemCount, OperationContext context) {
        this.itemCount = itemCount;
        this.hostname = context.getHostname();
        this.version = context.getVersion();
    }

    @SuppressWarnings("unused")
    public int getItemCount() {
        return itemCount;
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