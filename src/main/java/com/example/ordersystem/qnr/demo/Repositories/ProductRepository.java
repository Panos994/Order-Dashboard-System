package com.example.ordersystem.qnr.demo.Repositories;

import com.example.ordersystem.qnr.demo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
