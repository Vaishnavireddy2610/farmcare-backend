package com.farmcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmcare.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

}