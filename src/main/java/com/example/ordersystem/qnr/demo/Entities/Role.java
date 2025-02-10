package com.example.ordersystem.qnr.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
public class Role {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(length = 20)
        private String name;


        public Role() {

        }

        public Role(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }


}
