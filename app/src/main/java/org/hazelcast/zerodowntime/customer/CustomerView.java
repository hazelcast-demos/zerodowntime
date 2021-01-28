package org.hazelcast.zerodowntime.customer;

import org.hazelcast.zerodowntime.entity.Customer;

import java.io.Serializable;

public class CustomerView implements Serializable {

    private final Long id;
    private final String name;

    public CustomerView(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}