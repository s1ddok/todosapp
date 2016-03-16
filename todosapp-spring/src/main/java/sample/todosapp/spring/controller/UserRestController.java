package sample.todosapp.spring.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Provides REST API for Users
 */
@RestController
public class UserRestController {

    @RequestMapping("/api/users/self")
    public Principal self(Principal user) {
        return user;
    }

}

