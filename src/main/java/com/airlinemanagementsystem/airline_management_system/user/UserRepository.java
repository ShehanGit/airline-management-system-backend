package com.airlinemanagementsystem.airline_management_system.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findById(Integer id);

}
