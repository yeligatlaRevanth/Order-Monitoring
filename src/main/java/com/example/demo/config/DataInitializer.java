package com.example.demo.config;

import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeUsers(
            UserRepository userRepository,
            ProductRepository productRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("1234567"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("revanth").isEmpty()) {
                User user = new User();
                user.setUsername("revanth");
                user.setPassword(passwordEncoder.encode("1234567"));
                user.setRole(Role.USER);
                userRepository.save(user);
            }

            if (userRepository.findByUsername("user1").isEmpty()) {
                User user = new User();
                user.setUsername("user1");
                user.setPassword(passwordEncoder.encode("1234567"));
                user.setRole(Role.USER);
                userRepository.save(user);
            }

            if (userRepository.findByUsername("user2").isEmpty()) {
                User user = new User();
                user.setUsername("user2");
                user.setPassword(passwordEncoder.encode("1234567"));
                user.setRole(Role.USER);
                userRepository.save(user);
            }
            if (productRepository.count() == 0) {
                productRepository.saveAll(List.of(
                        new Product("Laptop", 55000.0),
                        new Product("Headphones", 2500.0),
                        new Product("Keyboard", 1500.0),
                        new Product("Mouse", 800.0),
                        new Product("Monitor", 12000.0)
                ));
            }
        };
    }
}