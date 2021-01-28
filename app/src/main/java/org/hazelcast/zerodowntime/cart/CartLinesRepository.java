package org.hazelcast.zerodowntime.cart;

import org.hazelcast.zerodowntime.entity.CartLine;
import org.hazelcast.zerodowntime.entity.CartLineId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartLinesRepository extends JpaRepository<CartLine, CartLineId> {

    List<CartLine> findAllByCustomer(Long customerId);
}