package flashcardApplication.user.repository;

import flashcardApplication.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String login);
    Optional<User> findByUsernameAndPassword(String login, String password);
}
