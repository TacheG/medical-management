package com.medical.backend.service;

import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void requestDoctor(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        user.setDoctorRequst(true); //trimite cererea

        userRepository.save(user);
    }

    public void aproveDoctor(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        user.setIsDoctor(true); //seteaza utilizatorul drept doctor

        userRepository.save(user);
    }
}