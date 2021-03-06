package com.sysc4806app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private String name;
    private User guy;

    @BeforeEach
    public void setUp() {
        name = "Bob";
        guy = new User(name);
    }

    @Test
    void getName() {
        assertEquals(name, guy.getName());
    }

    @Test
    void setName() {
        String newName = "Rob";
        guy.setName(newName);
        assertEquals(newName, guy.getName());
    }
}