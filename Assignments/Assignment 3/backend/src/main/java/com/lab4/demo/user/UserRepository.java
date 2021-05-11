package com.lab4.demo.user;

import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findUserByRoles(Role Roles);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
