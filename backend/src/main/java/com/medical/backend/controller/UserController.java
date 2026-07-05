package com.medical.backend.controller;

import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.AuthRequest;
import com.medical.backend.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/request-doctor/{id}")
    public String requestDoctor(@PathVariable Long id) {
        userService.requestDoctor(id);
        return "Success, your application was submited";
    }
}