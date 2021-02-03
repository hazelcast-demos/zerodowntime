package org.hazelcast.zerodowntime.customer;

import org.hazelcast.zerodowntime.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT cust FROM Customer cust LEFT JOIN FETCH cust.cart cart LEFT JOIN FETCH cart.cartLines WHERE cust.id = :id")
    Customer findByIdWithCartLines(@Param("id") Long id);
}