package com.example.warehouse.repository;


import com.example.warehouse.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

	Product findBySku(String sku);

	List<Product> findByVendor(String value);

}
