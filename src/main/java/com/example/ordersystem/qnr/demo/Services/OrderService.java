package com.example.ordersystem.qnr.demo.Services;

import com.example.ordersystem.qnr.demo.Entities.Orders;
import com.example.ordersystem.qnr.demo.Entities.Product;
import com.example.ordersystem.qnr.demo.Entities.User;
import com.example.ordersystem.qnr.demo.Repositories.OrderRepository;
import com.example.ordersystem.qnr.demo.Repositories.ProductRepository;
import com.example.ordersystem.qnr.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Orders placeOrder(Long productId, int quantity, String username) {
        // Retrieve the user based on username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve the product based on the product ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if there is enough stock for the order
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available.");
        }

        // Calculate the total cost (product cost * quantity)
        double totalCost = product.getCost() * quantity;

        // Create a new order and associate it with the user and product
        Orders newOrder = new Orders(product, quantity, user);
        newOrder.setTotalCost(totalCost);  // Set the calculated total cost

        // Save the order to the database
        orderRepository.save(newOrder);

        // Optionally, update the product stock
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        return newOrder;
    }
}


