package com.sysc4806app.repos;

import com.sysc4806app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, String> {
    List<Role> findAll();
    Role findByName(String name);
}
