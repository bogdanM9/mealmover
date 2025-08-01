package mealmover.backend.security.oauth2;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.responses.OAuth2UserInfoDto;
import mealmover.backend.models.ClientModel;
import mealmover.backend.services.ClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final ClientService clientService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 1. Call parent to get user info from Google
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. Process and save user to database
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfoDto userInfo = extractUserInfo(userRequest, oAuth2User);

        // Check if user exists, if not create new user
        ClientModel client = this.clientService.getOrCreateOAuth2Client(userInfo);

        return new OAuth2UserImpl(oAuth2User, client);
    }

    private OAuth2UserInfoDto extractUserInfo(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        if ("google".equals(registrationId)) {
            return OAuth2UserInfoDto.builder()
                .id((String) attributes.get("sub"))
                .email((String) attributes.get("email"))
                .firstName((String) attributes.get("given_name"))
                .lastName((String) attributes.get("family_name"))
                .profilePicture((String) attributes.get("picture"))
                .provider("google")
                .build();
        }

        throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
    }
}