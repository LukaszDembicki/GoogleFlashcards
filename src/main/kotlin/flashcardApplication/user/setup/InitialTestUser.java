package flashcardApplication.user.setup;

import flashcardApplication.user.entity.User;
import flashcardApplication.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Optional;

@Configuration
public class InitialTestUser {
    private static final Logger log = LoggerFactory.getLogger(InitialTestUser.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {

        ArrayList<User> userArrayList = new ArrayList<User>();
        userArrayList.add(this.getUser("user", "user"));
        userArrayList.add(this.getUser("testuser", "testuser"));

        for (User user : userArrayList) {
            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser.isEmpty()) {
                return args -> {
                    log.info("Preloading " + userRepository.save(user));
                };
            }
        }

        return null;
    }

    private User getUser(String username, String userPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(userPassword);
        user.setActive(true);
        user.setRoles(User.ROLE_USER);

        return user;
    }
}