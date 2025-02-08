package com.example.ordersystem.qnr.demo.REST;


import com.example.ordersystem.qnr.demo.Entities.Orders;
import com.example.ordersystem.qnr.demo.Entities.User;
import com.example.ordersystem.qnr.demo.Repositories.OrderRepository;
import com.example.ordersystem.qnr.demo.Repositories.UserRepository;
import com.example.ordersystem.qnr.demo.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/dashboard")
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Orders>> getUserOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Orders> orders = orderRepository.findByUser(user);
        return ResponseEntity.ok(orders);
    }
}
