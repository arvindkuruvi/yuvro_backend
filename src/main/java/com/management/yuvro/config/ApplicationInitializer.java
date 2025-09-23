package com.management.yuvro.config;

import com.management.yuvro.dto.request.RegisterRequest;
import com.management.yuvro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ApplicationInitializer implements CommandLineRunner {

    private final Keycloak keycloak;
    private final UserService userService;
    private final String username;
    private final String realm;

    public ApplicationInitializer(
            Keycloak keycloak, UserService userService,
            @Value("${keycloak.username}") String username,
            @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.username = username;
        this.userService = userService;
        this.realm = realm;
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminUserExists(keycloak)) {
            log.info("Admin user already exists, skipping creation");
        } else {
            log.info("Admin user does not exist, proceeding to create");
            RegisterRequest request = new RegisterRequest();
            request.setUsername(username);
            request.setEmail(username + "@gmail.com");
            request.setFirstName(username);
            request.setLastName(username);
            request.setType("admin");
            userService.registerUser(request);
        }
    }

    private boolean adminUserExists(Keycloak keycloak) {
        log.info("Checking if admin user exists in Keycloak");
        List<UserRepresentation> existingUsers = keycloak.realm(realm)
                .users()
                .search(username);

        for (UserRepresentation user : existingUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
