package mealmover.backend.security;

import lombok.RequiredArgsConstructor;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.UnauthorizedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
//    public boolean isAdmin() {
//        return isAuthenticated() && hasRole(Role.ADMIN);
//    }
//
//    public boolean isDriver() {
//        return isAuthenticated() && hasRole(Role.DRIVER);
//    }
//
//    public boolean isOperator() {
//        return isAuthenticated() && hasRole(Role.OPERATOR);
//    }
//
//    public boolean isClient() {
//        return isAuthenticated() && hasRole(Role.CLIENT);
//    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            return false;
        }

        if(!(authentication.isAuthenticated())) {
            return false;
        }

        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public String getLoggedInUserEmail() {
        if (!isAuthenticated()) {
            throw new UnauthorizedException("Not logged in");
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

//    private boolean hasRole(Role role) {
//        return SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch(authority -> authority.equals(role.toAuthority()));
//    }
}