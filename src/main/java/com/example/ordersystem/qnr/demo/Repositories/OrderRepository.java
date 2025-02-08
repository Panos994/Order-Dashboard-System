package com.example.ordersystem.qnr.demo.Repositories;

import com.example.ordersystem.qnr.demo.Entities.Orders;
import com.example.ordersystem.qnr.demo.Entities.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUser(User user);
}
