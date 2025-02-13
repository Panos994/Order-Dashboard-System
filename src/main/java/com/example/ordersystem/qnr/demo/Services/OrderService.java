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
        // I am retrieving the user based on username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // I am retrieving the product based on the productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));




        if (product.getQuantity() == 0) {
            throw new RuntimeException("The product is currently out of stock.");
        }

        // Checking if there is enough quantity(stock) for the order
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available.");
        }

        // Calculating total cost (product's cost * quantity)
        double totalCost = product.getCost() * quantity;

        // Creating order and associating it with the user and product
        Orders newOrder = new Orders(product, quantity, user);
        newOrder.setTotalCost(totalCost);  // Set the calculated total cost

        // Save order to the database
        orderRepository.save(newOrder);

        // update product's quantity
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        return newOrder;
    }
}


