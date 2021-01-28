package org.hazelcast.zerodowntime.customer;

import org.hazelcast.zerodowntime.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.cartLines where c.id = :id")
    Customer getOne(@Param("id") Long id);
}