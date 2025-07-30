package mealmover.backend.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test to verify OAuth2 dependencies are available on classpath
 */
class OAuth2ConfigurationTest {

    @Test
    void verifyOAuth2DependenciesAvailable() {
        // This test verifies that OAuth2 dependencies are available on classpath
        try {
            Class<?> oauth2UserServiceClass = Class.forName("org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService");
            Class<?> oauth2UserClass = Class.forName("org.springframework.security.oauth2.core.user.OAuth2User");
            Class<?> oauth2AuthenticationTokenClass = Class.forName("org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken");
            
            assertNotNull(oauth2UserServiceClass, "DefaultOAuth2UserService should be available");
            assertNotNull(oauth2UserClass, "OAuth2User should be available");
            assertNotNull(oauth2AuthenticationTokenClass, "OAuth2AuthenticationToken should be available");
            
            assertTrue(true, "OAuth2 dependencies are properly configured");
        } catch (ClassNotFoundException e) {
            throw new AssertionError("OAuth2 dependencies not found: " + e.getMessage());
        }
    }

    @Test
    void verifyCustomOAuth2ServiceExists() {
        // Verify our custom OAuth2UserService class exists
        try {
            Class<?> customOAuth2Service = Class.forName("mealmover.backend.security.OAuth2UserService");
            assertNotNull(customOAuth2Service, "Custom OAuth2UserService should exist");
            assertTrue(true, "Custom OAuth2UserService is properly defined");
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Custom OAuth2UserService not found: " + e.getMessage());
        }
    }
}