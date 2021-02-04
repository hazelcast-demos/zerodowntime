package org.hazelcast.zerodowntime;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class CallParameters {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallParameters.class);

    private final int customerId;
    private final int productId;
    private final int quantity;

    public CallParameters(int customerId, int productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CallParameters{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

    public static CallParameters toCallParameters(Map<String, Object> map) {
        var customerId = (int) map.get("CUSTOMER_ID");
        var productId = (int) map.get("PRODUCT_ID");
        var quantity = (int) map.get("QUANTITY");
        var parameters = new CallParameters(customerId, productId, quantity);
        LOGGER.info("Created parameters {}", parameters);
        return parameters;
    }
}