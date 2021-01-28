package org.hazelcast.zerodowntime.cart;

public class CartView {

    private final int itemCount;

    public CartView(int itemCount) {
        this.itemCount = itemCount;
    }

    @SuppressWarnings("unused")
    public int getItemCount() {
        return itemCount;
    }
}