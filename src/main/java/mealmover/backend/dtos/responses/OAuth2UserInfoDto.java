package mealmover.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuth2UserInfoDto {
    private String id;        // Google's unique user ID
    private String email;     // User's email
    private String firstName; // Given name from Google
    private String lastName;  // Family name from Google
    private String profilePicture; // URL to profile image
    private String provider;  // "google", "facebook", etc.
}