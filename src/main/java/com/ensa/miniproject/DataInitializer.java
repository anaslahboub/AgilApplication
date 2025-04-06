package com.ensa.miniproject;

import com.ensa.miniproject.security.UserAuth;
import com.ensa.miniproject.security.UserAuthRep;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserAuthRep userAuthRep;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (userAuthRep.findByUsername("admin").isEmpty()) {
            UserAuth admin = new UserAuth();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of("ADMIN"));
            userAuthRep.save(admin);
            System.out.println("✅ Admin user created successfully");
        }

        // Create product owner user if not exists
        if (userAuthRep.findByUsername("productowner").isEmpty()) {
            UserAuth po = new UserAuth();
            po.setUsername("productowner");
            po.setPassword(passwordEncoder.encode("po123"));
            po.setRoles(Set.of("PRODUCT_OWNER"));
            userAuthRep.save(po);
            System.out.println("✅ Product Owner user created successfully");
        }

        // Create scrum master user if not exists
        if (userAuthRep.findByUsername("scrummaster").isEmpty()) {
            UserAuth sm = new UserAuth();
            sm.setUsername("scrummaster");
            sm.setPassword(passwordEncoder.encode("sm123"));
            sm.setRoles(Set.of("SCRUM_MASTER"));
            userAuthRep.save(sm);
            System.out.println("✅ Scrum Master user created successfully");
        }

        // Create developer user if not exists
        if (userAuthRep.findByUsername("developer").isEmpty()) {
            UserAuth dev = new UserAuth();
            dev.setUsername("developer");
            dev.setPassword(passwordEncoder.encode("dev123"));
            dev.setRoles(Set.of("DEVELOPER"));
            userAuthRep.save(dev);
            System.out.println("✅ Developer user created successfully");
        }
    }
}