package com.sysc4806app.configuration;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class CurrentUserAdvice {

    @ModelAttribute("navUser")
    public String getNavigatingUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth instanceof AnonymousAuthenticationToken) ?
                null : SecurityContextHolder.getContext().getAuthentication().getName();
    }
}