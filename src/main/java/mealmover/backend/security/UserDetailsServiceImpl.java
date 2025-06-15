package mealmover.backend.security;

import lombok.RequiredArgsConstructor;
import mealmover.backend.models.UserModel;
import mealmover.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Optional<UserModel> optionalUserModel = this.userRepository.findByEmail(email);

        if(optionalUserModel.isEmpty()) {
            return null;
        }

        return UserDetailsImpl.build(optionalUserModel.get());
    }
}