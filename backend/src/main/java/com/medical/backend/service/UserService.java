package com.medical.backend.service;

import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String requestDoctor(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().setDoctorRequest(true);

            userRepository.save(user.get());
            return "Success, your application was submited";
        } else {
            return "User not found";
        }

    }

    public String approveDoctor(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            user.get().setDoctorRequest(false);
            user.get().setDoctor(true);

            userRepository.save(user.get());
            return "Success, your application was approved";
        } else {
            return "User not found";
        }
    }
}