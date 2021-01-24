package flashcardApplication.flashcards.application.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import flashcardApplication.SanityBootTest;
import flashcardApplication.flashcards.helper.FlashcardsTestHelper;
import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import flashcardApplication.flashcards.infrastructure.repository.FlashcardGroupRepository;
import flashcardApplication.user.helper.UserTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

public class FlashcardGroupControllerTest extends SanityBootTest {

    UserTestHelper userTestHelper;
    FlashcardsTestHelper flashcardsTestHelper;
    FlashcardGroupRepository flashcardGroupRepository;

    @Autowired
    public FlashcardGroupControllerTest(
            MockMvc mockMvc,
            UserTestHelper userTestHelper,
            FlashcardsTestHelper flashcardsTestHelper,
            FlashcardGroupRepository flashcardGroupRepository
    ) {
        super(mockMvc);
        this.userTestHelper = userTestHelper;
        this.flashcardsTestHelper = flashcardsTestHelper;
        this.flashcardGroupRepository = flashcardGroupRepository;
    }

    @Test
    public void testGetAllFlashcardGroupWithFlashcards() throws Exception {
        this.flashcardGroupRepository.deleteByUserId(this.userTestHelper.getTestUserOrCreate().getId());

        List<FlashcardGroup> createdFlashcardGroups = new ArrayList<>();
        createdFlashcardGroups.add(this.flashcardsTestHelper.saveFlashcardGroupWithFlashcard(2));

        String responseContent = this.flashcardsTestHelper.sendRequestGet("/api/v2/flashcards/groups/all").andReturn().getResponse().getContentAsString();
        List<FlashcardGroup> responseFlashcardGroups = new Gson().fromJson(responseContent, new TypeToken<List<FlashcardGroup>>() {
        }.getType());

        for (int i = 0; i < createdFlashcardGroups.size(); i++) {
            Assertions.assertEquals(createdFlashcardGroups.get(i).getId(), responseFlashcardGroups.get(i).getId());
            Assertions.assertEquals(createdFlashcardGroups.get(i).getName(), responseFlashcardGroups.get(i).getName());
            Assertions.assertEquals(createdFlashcardGroups.get(i).getOrderNumber(), responseFlashcardGroups.get(i).getOrderNumber());
        }

        for (FlashcardGroup flashcardGroupToDelete : createdFlashcardGroups) {
            this.flashcardGroupRepository.deleteById(flashcardGroupToDelete.getId());
        }
    }

    @Test
    public void testUpdateFlashcardKnowledgeType() throws Exception {
        this.flashcardGroupRepository.deleteByUserId(this.userTestHelper.getTestUserOrCreate().getId());

        FlashcardGroup flashcardGroup = this.flashcardsTestHelper.saveFlashcardGroupWithFlashcard(1);
        String flashcardGroupId = flashcardGroup.getId();
        Flashcard flashcard = flashcardGroup.getFlashcardList().get(0);

        this.flashcardsTestHelper.sendRequestPost("/api/v2/flashcards/update-flashcard-type", new Gson().toJson(flashcard), null);

        FlashcardGroup databaseFlashcardGroup = this.flashcardGroupRepository.findById(flashcardGroupId).get();
        Flashcard databaseFlashcard = databaseFlashcardGroup.getFlashcardList().get(0);

        Assertions.assertEquals(flashcard.getKnowledgeType(), databaseFlashcard.getKnowledgeType());
        Assertions.assertNotNull(databaseFlashcard.getUpdatedAt());

        this.flashcardGroupRepository.deleteById(flashcardGroupId);
    }
}
