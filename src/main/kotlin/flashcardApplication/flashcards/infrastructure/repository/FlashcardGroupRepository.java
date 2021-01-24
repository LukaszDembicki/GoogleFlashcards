package flashcardApplication.flashcards.infrastructure.repository;

import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlashcardGroupRepository extends MongoRepository<FlashcardGroup, String>, FlashcardGroupModifyRepository {
    FlashcardGroup findByIdAndUserId(String id, String userId);

    List<FlashcardGroup> findAllByUserId(String userId);

    void deleteByUserId(String userId);
}
