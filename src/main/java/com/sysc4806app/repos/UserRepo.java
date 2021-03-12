package com.sysc4806app.repos;

import com.sysc4806app.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User,Long> {

    List<User> findAll();
    User findById(long id);
    User findByName(String name);

}
