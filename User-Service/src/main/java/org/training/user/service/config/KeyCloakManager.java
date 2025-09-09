package org.training.user.service.config;

import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakManager {

    private final KeyCloakProperties keyCloakProperties;

    // Constructor to initialize final field
    public KeyCloakManager(KeyCloakProperties keyCloakProperties) {
        this.keyCloakProperties = keyCloakProperties;
    }

    public RealmResource getKeyCloakInstanceWithRealm() {
        return keyCloakProperties.getKeycloakInstance().realm(keyCloakProperties.getRealm());
    }
}
