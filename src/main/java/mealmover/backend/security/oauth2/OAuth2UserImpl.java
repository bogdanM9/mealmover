package mealmover.backend.security.oauth2;

import lombok.Getter;
import mealmover.backend.models.ClientModel;
import mealmover.backend.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuth2UserImpl implements OAuth2User {
    private final OAuth2User oAuth2User;
    private final UserDetailsImpl userDetails;

    public OAuth2UserImpl(OAuth2User oAuth2User, ClientModel client) {
        this.oAuth2User = oAuth2User;
        this.userDetails = UserDetailsImpl.build(client);
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }
}