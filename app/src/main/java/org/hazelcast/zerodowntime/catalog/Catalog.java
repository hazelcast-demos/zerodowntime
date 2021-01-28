package org.hazelcast.zerodowntime.catalog;

import org.hazelcast.zerodowntime.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Catalog extends JpaRepository<Product, Long> {
}
