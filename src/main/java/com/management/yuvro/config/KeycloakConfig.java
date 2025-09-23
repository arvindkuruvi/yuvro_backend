package com.management.yuvro.config;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.management.yuvro.constants.Constants.ADMIN_ROLE;

@Configuration
@Slf4j
public class KeycloakConfig {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Bean
    public Keycloak keycloak() {
        log.info("Keycloak Server URL: {}", serverUrl);
        log.info("Keycloak Realm: {}", realm);
        log.info("Keycloak Client ID: {}", clientId);
        log.info("Keycloak Client Secret: {}", clientSecret);
        log.info("Keycloak Username: {}", username);
        log.info("Keycloak Password: {}", password);
        log.info("Initializing Keycloak Bean");
//        var keycloak = KeycloakBuilder.builder()
//                .serverUrl(serverUrl)
//                .realm(realm)
//                .grantType(OAuth2Constants.PASSWORD)
//                .username(username)
//                .password(password)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .build();

        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }


    private void testLogin(Keycloak keycloak) {
        try {
            log.info("✅ Connected to Keycloak, listing users...");
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            log.info("Found {} user(s) in realm '{}'", users.size(), realm);
        } catch (Exception e) {
            log.error("❌ Failed to connect to Keycloak Admin API", e);
            throw e;
        }
    }

    private void initKeycloakSettings(Keycloak keycloak) {
        log.info("Initializing Keycloak settings");

        log.info("Adding protocol mapper to client: {}", clientId);
        // Step 1: Fetch internal client ID
        String internalClientId = keycloak.realm(realm)
                .clients()
                .findByClientId(clientId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId))
                .getId();

        log.info("Internal Client ID for {}: {}", clientId, internalClientId);
        // Step 2: Get the client representation
        ClientRepresentation client = keycloak.realm(realm)
                .clients()
                .get(internalClientId)
                .toRepresentation();

        log.info("Client Representation fetched");
        // Step 3: Create the protocol mapper
        ProtocolMapperRepresentation roleMapper = new ProtocolMapperRepresentation();
        roleMapper.setName("roles");
        roleMapper.setProtocol("openid-connect");
        roleMapper.setProtocolMapper("oidc-usermodel-realm-role-mapper");
        roleMapper.getConfig().put("consent.required", "false");
        roleMapper.getConfig().put("claim.name", "realm_access.roles");
        roleMapper.getConfig().put("jsonType.label", "String");
        roleMapper.getConfig().put("id.token.claim", "true");
        roleMapper.getConfig().put("access.token.claim", "true");

        log.info("Protocol Mapper created");
        // Step 4: Add the protocol mapper directly to the client
//        client.getProtocolMappers().add(roleMapper);

        List<ProtocolMapperRepresentation> mapperList = new ArrayList<>();
        mapperList.add(roleMapper);
        client.setProtocolMappers(mapperList);

        log.info("Protocol Mapper added to client");
        // Step 5: Update the client with the new mapper
        keycloak.realm(realm).clients().get(internalClientId).update(client);


//        ClientRepresentation client = keycloak.realm(realm).clients().get(clientId).toRepresentation();
//        ClientScopeRepresentation clientScope = new ClientScopeRepresentation();
//        clientScope.setName("roles");
//        clientScope.setProtocol("openid-connect");
//
//        ProtocolMapperRepresentation roleMapper = new ProtocolMapperRepresentation();
//        roleMapper.setName("roles");
//        roleMapper.setProtocol("openid-connect");
//        roleMapper.setProtocolMapper("oidc-usermodel-realm-role-mapper");
//        roleMapper.getConfig().put("consent.required", "false");
//        roleMapper.getConfig().put("claim.name", "realm_access.roles");
//        roleMapper.getConfig().put("jsonType.label", "String");
//        roleMapper.getConfig().put("id.token.claim", "true");
//        roleMapper.getConfig().put("access.token.claim", "true");
//
//        clientScope.getProtocolMappers().add(roleMapper);
//
//        // Add the client scope to the client
//        keycloak.realm(realm).clients().get(clientId).update(client);
        log.info("Keycloak settings initialization completed");
    }

    private void initRoles(Keycloak keycloak) {
        log.info("Initializing default roles in Keycloak");
        RealmResource realmResource = keycloak.realm(realm);

        List<String> rolesToEnsure = Arrays.asList(ADMIN_ROLE, "STUDENT", "FACULTY");

        for (String roleName : rolesToEnsure) {
            boolean exists = realmResource.roles().list().stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase(roleName));

            if (!exists) {
                var role = new RoleRepresentation();
                role.setName(roleName);
                realmResource.roles().create(role);
                log.info("Created role: {}", roleName);
            } else {
                log.info("Role already exists: {}", roleName);
            }
        }
        log.info("Default roles initialization completed");
    }
}
