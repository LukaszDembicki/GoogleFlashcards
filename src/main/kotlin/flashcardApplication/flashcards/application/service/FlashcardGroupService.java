package flashcardApplication.flashcards.application.service;

import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import flashcardApplication.flashcards.infrastructure.repository.FlashcardGroupRepository;
import flashcardApplication.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardGroupService {
    private final FlashcardGroupRepository flashcardGroupRepository;
    private final UserService userService;

    @Autowired
    public FlashcardGroupService(
            FlashcardGroupRepository flashcardGroupRepository,
            UserService userService
    ) {
        this.flashcardGroupRepository = flashcardGroupRepository;
        this.userService = userService;
    }

    public List<FlashcardGroup> getFlashcardGroupWithFlashcards() {
        List<FlashcardGroup> flashcardGroups = this.flashcardGroupRepository.
                findAllByUserId(this.userService.getCurrentUserId());

        this.createFirstFlashcardGroupIfNoneExists(flashcardGroups);

        return flashcardGroups;
    }

    public void updateFlashcardKnowledgeType(Flashcard flashcard) {
        if (flashcard.getId() == null || flashcard.getKnowledgeType() == null) {
            return;
        }

        this.flashcardGroupRepository.updateFlashcardKnowledgeTypeById(
                flashcard.getId(),
                flashcard.getKnowledgeType(),
                this.userService.getCurrentUserId()
        );
    }

    private void createFirstFlashcardGroupIfNoneExists(List<FlashcardGroup> flashcardGroups) {
        if (flashcardGroups.size() == 0) {
            FlashcardGroup initialFlashcardGroup = new FlashcardGroup();
            initialFlashcardGroup.setName("First flashcard group");
            initialFlashcardGroup.setOrderNumber(1);
            initialFlashcardGroup.setUserId(this.userService.getCurrentUserId());

            initialFlashcardGroup.setId(this.createFlashcardGroup(initialFlashcardGroup));

            flashcardGroups.add(initialFlashcardGroup);
        }
    }

    private String createFlashcardGroup(FlashcardGroup flashcardGroup) {
        return this.flashcardGroupRepository.save(flashcardGroup).getId();
    }
}
