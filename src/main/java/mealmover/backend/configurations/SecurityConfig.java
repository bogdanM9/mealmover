package mealmover.backend.configurations;

import lombok.RequiredArgsConstructor;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.security.EntryPointAuthentication;
import mealmover.backend.security.JwtAuthenticationFilter;
import mealmover.backend.security.JwtAuthenticationProvider;
import mealmover.backend.security.LogoutService;
import mealmover.backend.services.UserService;
import mealmover.backend.services.auth.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] WHITE_LIST_URL = {
        "/auth/register",
        "/auth/activate",
        "/auth/login",
        "/auth/forgot-password",
        "/auth/reset-password"
    };

    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final LogoutService logoutService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final EntryPointAuthentication entryPointAuthentication;

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtService, userMapper, userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(jwtAuthenticationProvider())
            .authorizeHttpRequests(req -> req
                .requestMatchers(WHITE_LIST_URL).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception.authenticationEntryPoint(this.entryPointAuthentication))
            .logout(
                logout ->
                    logout.logoutUrl("/auth/logout")
                        .addLogoutHandler(this.logoutService)
                        .logoutSuccessHandler(new learnsql.backend.security.LogoutHandlerSuccess())
            );

        return http.build();
    }
}