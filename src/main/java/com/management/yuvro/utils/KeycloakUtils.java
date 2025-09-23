package com.management.yuvro.utils;

import com.management.yuvro.dto.response.AuthResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.management.yuvro.constants.Constants.ADMIN_ROLE;

@Slf4j
@Service
public class KeycloakUtils {
    private final Keycloak keycloak;
    private final RealmResource realmResource;
    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final String realm;

    public KeycloakUtils(
            Keycloak keycloak,
            @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}") String tokenUrl,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret,
            @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realm = realm;
        log.info("KeycloakUtils initialized with tokenUrl: {}, clientId: {}, realm: {}", tokenUrl, clientId, realm);
        this.realmResource = keycloak.realm(realm);
    }

    public void createUser(String username, String firstName, String lastName, String email, String password, String role) {
        log.info("Creating user: {} with role: {}", username, role);
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setEnabled(true);

        RealmResource realmResource = keycloak.realm(realm);
        log.info("User representation created for : {} - {}", username, user);
        // Step 2: Create the user in Keycloak
        Response response = realmResource.users().create(user);
        if (response.getStatus() != 201) {
            log.error("Failed to create user: status={}, entity={}", response.getStatus(), response.readEntity(String.class));
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }

        log.info("User created in Keycloak: {}", username);

        log.info("Setting initial password for user: {}", username);
        // Create user in Keycloak
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        updatePassword(userId, password);
        log.info("Password set for user: {}", username);

        log.info("Assigning role: {} to user: {}", role, username);

        if (role.equalsIgnoreCase("admin")) {
            role = ADMIN_ROLE;
        } else if (role.equalsIgnoreCase("student")) {
            role = "STUDENT";
        } else if (role.equalsIgnoreCase("faculty")) {
            role = "FACULTY";
        } else {
            throw new BadRequestException("Invalid role: " + role);
        }

        // Assign role to the user
        RoleRepresentation roleRepresentation = realmResource.roles().get(role).toRepresentation();
        realmResource.users().get(userId).roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        log.info("Role: {} assigned to user: {}", role, username);
    }

    public void updatePassword(String userId, String newPassword) {
        log.info("Updating password for user ID: {}", userId);
        UserResource userResource = realmResource.users().get(userId);

        // Create CredentialRepresentation for password
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);  // set to false if this is not a temporary password

        // Reset the password
        userResource.resetPassword(credential);
        log.info("Password updated successfully for user ID: {}", userId);
    }


    public void resetPasswordByUsername(String username, String newPassword) {
        // 1. Search user by username
        List<UserRepresentation> users = realmResource.users().search(username, 0, 1);

        if (users.isEmpty()) {
            throw new RuntimeException("User not found with username: " + username);
        }

        // 2. Get the user ID
        UserRepresentation user = users.get(0);
        String userId = user.getId();
        // 3. Update the password
        updatePassword(userId, newPassword);
    }

    public void deleteUserByUsername(String email) {
        log.info("Deleting user with email: {}", email);
        List<UserRepresentation> users = realmResource.users().search(email, 0, 1);

        if (users.isEmpty()) {
            throw new RuntimeException("User not found with username: " + email);
        }

        log.info("User found: {} - {}", email, users.get(0));
        String userId = users.get(0).getId();

        log.info("Deleting user with ID: {}", userId);
        realmResource.users().get(userId).remove();
        log.info("User deleted successfully: {}", email);
    }

    public AuthResponse getToken(String username, String password) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("grant_type", "password");
            form.add("client_id", clientId);
            form.add("client_secret", clientSecret);
            form.add("username", username);
            form.add("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

            ResponseEntity<AuthResponse> response = restTemplate.postForEntity(tokenUrl, request, AuthResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Login failed: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new BadRequestException("Failed to authenticate user: " + e.getMessage());
        }
    }

}
