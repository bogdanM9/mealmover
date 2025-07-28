package mealmover.backend.services.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.info("Checking if user exists by email: {}", email);
        return this.userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        log.info("Checking if user exists by phone number: {}", phoneNumber);
        return this.userRepository.existsByPhoneNumber(phoneNumber);
    }
}
