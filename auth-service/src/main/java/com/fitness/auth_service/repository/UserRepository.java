package com.fitness.auth_service.repository;


import com.fitness.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
