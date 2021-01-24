package flashcardApplication.flashcards.infrastructure.repository;

public interface FlashcardGroupModifyRepository {
    void updateFlashcardKnowledgeTypeById(String id, String knowledgeType, String userId);
}
