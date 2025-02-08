package com.example.ordersystem.qnr.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private int quantity;
        private double totalCost;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "create_time", nullable = false, updatable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date createTime;

        public Orders(Product product, int quantity, User user) {
                this.product = product;
                this.quantity = quantity;
                this.user = user;
                this.totalCost = product.getCost() * quantity;
                this.createTime = new Date();
        }
}



