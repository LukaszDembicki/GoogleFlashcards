package flashcardApplication.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import flashcardApplication.user.model.GoogleAuthRequest;
import flashcardApplication.user.model.UserDetails;
import flashcardApplication.user.entity.User;
import flashcardApplication.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        List<GrantedAuthority> rolesList = new ArrayList<>();
        rolesList.add(new SimpleGrantedAuthority(user.get().getRoles()));

        UserDetails userDetails = new UserDetails(
                user.get().getId(),
                user.get().getUsername(),
                user.get().getPassword(),
                rolesList        // List<GrandedAuthority>
        );

        return userDetails;
    }

    public String getCurrentUserId() {
        return this.getCurrentUser().getId();
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = new User();
        user.setId(userDetails.getUserId());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setActive(userDetails.isEnabled());
//        user.setRoles(userDetails.getAuthorities());

        return user;
    }

    public User registerOrGetUser(GoogleAuthRequest authRequest) throws InvalidParameterException, GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(this.googleClientId))
                .build();

        GoogleIdToken idToken = verifier.verify(authRequest.getIdToken());
        if (idToken == null) {
            throw new InvalidParameterException("Google token is invalid!");
        }

        Payload payload = idToken.getPayload();

        Optional<User> user = this.userRepository.findByUsernameAndPassword(payload.getEmail(), "password");
        if (user.isPresent()) {
            return user.get();
        }

        User newUser = new User();
        newUser.setUsername(payload.getEmail());
        newUser.setPassword("password");
        newUser.setActive(User.USER_ACTIVE_TRUE);
        newUser.setRoles(User.ROLE_USER);

        this.userRepository.save(newUser);

        return newUser;
    }

    private void getUserDetails(Payload payload) {
        String userId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
    }
}
