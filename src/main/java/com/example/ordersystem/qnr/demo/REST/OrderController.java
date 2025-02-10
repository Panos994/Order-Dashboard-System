package com.example.ordersystem.qnr.demo.REST;

import com.example.ordersystem.qnr.demo.DTO.OrderRequest;
import com.example.ordersystem.qnr.demo.Entities.Orders;
import com.example.ordersystem.qnr.demo.Entities.Product;
import com.example.ordersystem.qnr.demo.Entities.User;
import com.example.ordersystem.qnr.demo.Repositories.OrderRepository;
import com.example.ordersystem.qnr.demo.Repositories.ProductRepository;
import com.example.ordersystem.qnr.demo.Repositories.UserRepository;
import com.example.ordersystem.qnr.demo.Services.OrderService;
import com.example.ordersystem.qnr.demo.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping("/submit")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Call the service to handle the order placement logic
            Orders newOrder = orderService.placeOrder(orderRequest.getProductId(), orderRequest.getQuantity(), userDetails.getUsername());
            return ResponseEntity.ok(newOrder);
        } catch (RuntimeException e) {
            // Handle any errors that occur during the order placement
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Orders>> getUserOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(orderRepository.findByUser(user));
    }
}
