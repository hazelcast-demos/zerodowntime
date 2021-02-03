package org.hazelcast.zerodowntime.cart;

import org.hazelcast.zerodowntime.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT cart FROM Cart cart, Customer cust LEFT JOIN FETCH cart.cartLines WHERE cust.id = :id AND cust.cart = cart")
    Cart findWithLinesByCustomerId(@Param("id") Long customerId);

    @Query("DELETE FROM CartLine line WHERE line IN (SELECT line FROM CartLine line, Customer customer WHERE customer.id = :customerId AND line.id.product.id = :productId)")
    @Transactional
    @Modifying
    void deleteByCustomerIdAndProductId(@Param("customerId") Long customerId, @Param("productId") Long productId);
}