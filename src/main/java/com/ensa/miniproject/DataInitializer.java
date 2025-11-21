package com.ensa.miniproject;

import com.ensa.miniproject.security.UserAuth;
import com.ensa.miniproject.security.UserAuthRep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserAuthRep userAuthRep;
    private final PasswordEncoder passwordEncoder;

    // Injection des mots de passe depuis application.properties
    @Value("${app.default-users.admin-pass}")
    private String adminPass;

    @Value("${app.default-users.po-pass}")
    private String poPass;

    @Value("${app.default-users.sm-pass}")
    private String smPass;

    @Value("${app.default-users.dev-pass}")
    private String devPass;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (userAuthRep.findByUsername("admin").isEmpty()) {
            UserAuth admin = new UserAuth();
            admin.setUsername("admin");
            // Utilisation de la variable injectée au lieu du texte "admin123"
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setRoles(Set.of("ADMIN"));
            userAuthRep.save(admin);
            log.info("✅ Admin user created successfully");
        }

        // Create product owner user if not exists
        if (userAuthRep.findByUsername("productowner").isEmpty()) {
            UserAuth po = new UserAuth();
            po.setUsername("productowner");
            po.setPassword(passwordEncoder.encode(poPass));
            po.setRoles(Set.of("PRODUCT_OWNER"));
            userAuthRep.save(po);
            log.info("✅ Product Owner user created successfully");
        }

        // Create scrum master user if not exists
        if (userAuthRep.findByUsername("scrummaster").isEmpty()) {
            UserAuth sm = new UserAuth();
            sm.setUsername("scrummaster");
            sm.setPassword(passwordEncoder.encode(smPass));
            sm.setRoles(Set.of("SCRUM_MASTER"));
            userAuthRep.save(sm);
            log.info("✅ Scrum Master user created successfully");
        }

        // Create developer user if not exists
        if (userAuthRep.findByUsername("developer").isEmpty()) {
            UserAuth dev = new UserAuth();
            dev.setUsername("developer");
            dev.setPassword(passwordEncoder.encode(devPass));
            dev.setRoles(Set.of("DEVELOPER"));
            userAuthRep.save(dev);
            log.info("✅ Developer user created successfully");
        }
    }
}