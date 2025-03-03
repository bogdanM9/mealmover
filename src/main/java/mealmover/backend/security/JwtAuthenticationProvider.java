package mealmover.backend.security;

import lombok.RequiredArgsConstructor;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.models.UserModel;
import mealmover.backend.services.UserService;
import mealmover.backend.services.auth.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Attempting to authenticate user with JWT token");

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new UnauthorizedException("Invalid authentication class not instance of UsernamePasswordAuthenticationToken");
        }

        String token = (String) authentication.getCredentials();
        String email = jwtService.extractEmail(token);

        UserModel userModel = this.userService.getModelByEmail(email);

        UserDetails userDetails = this.userMapper.toUserDetails(userModel);

        if(!jwtService.isTokenValid(token, userDetails, Token.ACCESS.toCamelCase())) {
            throw new UnauthorizedException("Invalid JWT token");
        }

        logger.info("User with email {} successfully authenticated", email);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}