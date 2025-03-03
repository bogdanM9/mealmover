package mealmover.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.enums.Token;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.models.UserModel;
import mealmover.backend.services.UserService;
import mealmover.backend.services.auth.JwtService;
import mealmover.backend.utils.CookieUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("Attempting to pass through JWT authentication filter");

        String token = CookieUtils.extractTokenFromCookies(request, Token.ACCESS.toCamelCase());

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = this.jwtService.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserModel userModel = this.userService.getModelByEmail(email);

            UserDetails userDetails = this.userMapper.toUserDetails(userModel);

            boolean isTokenValid = this.jwtService.isTokenValid(
                token,
                userDetails,
                Token.ACCESS.toCamelCase()
            );

            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.info("Successfully passed through JWT authentication filter");
            }
        }

        filterChain.doFilter(request, response);
    }
}