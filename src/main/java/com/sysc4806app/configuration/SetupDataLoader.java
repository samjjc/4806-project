package com.sysc4806app.configuration;

import com.sysc4806app.model.Role;
import com.sysc4806app.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final RoleRepo roleRepo;

    @Autowired
    public SetupDataLoader(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        alreadySetup = true;
    }

    @Transactional
    void createRoleIfNotFound(String name) {
        if (roleRepo.findByName(name) == null) {
            roleRepo.save(new Role(name));
        }
    }
}