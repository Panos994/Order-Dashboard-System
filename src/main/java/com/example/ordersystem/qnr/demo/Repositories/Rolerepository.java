package com.example.ordersystem.qnr.demo.Repositories;

import com.example.ordersystem.qnr.demo.Entities.Role;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@Hidden
public interface Rolerepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
