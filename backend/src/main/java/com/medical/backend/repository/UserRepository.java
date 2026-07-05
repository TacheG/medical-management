package com.medical.backend.repository;

import com.medical.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);

    List<User> findByDoctorRequestTrue(); // lista de utilizatori care au cerut aprobare de la admini pentru a fi doctori
}
