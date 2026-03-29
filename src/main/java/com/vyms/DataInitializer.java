package com.vyms;

import com.vyms.entity.Role;
import com.vyms.entity.User;
import com.vyms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            createUserIfMissing(userRepository, passwordEncoder, "Admin", "admin@vehicleyard.com", "password",
                    Role.ADMIN);
            createUserIfMissing(userRepository, passwordEncoder, "Manager", "manager@vehicleyard.com", "password",
                    Role.MANAGER);
            createUserIfMissing(userRepository, passwordEncoder, "Sales", "sales@vehicleyard.com", "password",
                    Role.SALES);
            createUserIfMissing(userRepository, passwordEncoder, "Inventory", "inventory@vehicleyard.com", "password",
                    Role.INVENTORY);
            createUserIfMissing(userRepository, passwordEncoder, "Mechanic", "mechanic@vehicleyard.com", "password",
                    Role.MECHANIC);
        };
    }

    private void createUserIfMissing(UserRepository repo, BCryptPasswordEncoder encoder, String username, String email,
            String password, Role role) {
        if (repo.findByEmail(email).isPresent()) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        repo.save(user);
    }
}
