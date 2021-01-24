package flashcardApplication.flashcards.application.controller;

import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.application.service.FlashcardGroupService;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlashcardGroupController {
    private final FlashcardGroupService mongoFlashcardGroupService;

    @Autowired
    public FlashcardGroupController(FlashcardGroupService mongoFlashcardGroupService) {
        this.mongoFlashcardGroupService = mongoFlashcardGroupService;
    }

    @GetMapping("/api/v2/flashcards/groups/all")
    public List<FlashcardGroup> getFlashcardGroupWithFlashcards() {
        return this.mongoFlashcardGroupService.getFlashcardGroupWithFlashcards();
    }

    @PostMapping("/api/v2/flashcards/update-flashcard-type")
    public void saveFlashcardGroupValues(@RequestBody Flashcard flashcard) {
        this.mongoFlashcardGroupService.updateFlashcardKnowledgeType(flashcard);
    }
}
