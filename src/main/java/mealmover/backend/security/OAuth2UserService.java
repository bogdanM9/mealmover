package mealmover.backend.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.enums.Role;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.models.UserModel;
import mealmover.backend.services.ClientService;
import mealmover.backend.services.RoleService;
import mealmover.backend.services.data.UserDataService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    
    private final UserDataService userDataService;
    private final ClientService clientService;
    private final RoleService roleService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        return processOAuth2User(oauth2User);
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        
        log.info("Processing OAuth2 user with email: {}", email);
        
        UserModel userModel;
        
        // Check if user already exists
        if (userDataService.existsByEmail(email)) {
            userModel = userDataService.getByEmail(email);
            log.info("Found existing user: {}", email);
        } else {
            // Create new user
            userModel = createNewOAuth2User(email, firstName, lastName);
            log.info("Created new OAuth2 user: {}", email);
        }
        
        return new UserDetailsImpl(userModel, attributes);
    }
    
    private UserModel createNewOAuth2User(String email, String firstName, String lastName) {
        ClientModel clientModel = new ClientModel();
        clientModel.setEmail(email);
        clientModel.setFirstName(firstName != null ? firstName : "");
        clientModel.setLastName(lastName != null ? lastName : "");
        // Set a placeholder password - OAuth2 users don't need passwords
        clientModel.setPassword("OAUTH2_USER");
        // Set a placeholder phone number - will need to be updated by user later
        clientModel.setPhoneNumber("000-000-0000-" + System.currentTimeMillis());
        
        // Add CLIENT role
        RoleModel clientRole = roleService.getModelByName(Role.CLIENT.toCapitalize());
        clientModel.addRole(clientRole);
        
        return clientService.create(clientModel);
    }
}