package flashcardApplication.flashcards.helper;

import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import flashcardApplication.flashcards.infrastructure.repository.FlashcardGroupRepository;
import flashcardApplication.user.helper.UserTestHelper;
import flashcardApplication.user.repository.UserRepository;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class FlashcardsTestHelper {

    UserTestHelper userTestHelper;
    FlashcardGroupRepository flashcardGroupRepository;
    MockMvc mockMvc;
    UserRepository userRepository;

    @Autowired
    public FlashcardsTestHelper(
            UserTestHelper userTestHelper,
            FlashcardGroupRepository flashcardGroupRepository,
            MockMvc mockMvc,
            UserRepository userRepository
    ) {
        this.userTestHelper = userTestHelper;
        this.flashcardGroupRepository = flashcardGroupRepository;
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
    }

    public ResultActions sendRequestDelete(String url) throws Exception {
        return this.mockMvc.perform(delete(url).header("Authorization", this.userTestHelper.getBearer()))
                .andDo(print()).andExpect(status().isOk());
    }

    public ResultActions sendRequestGet(String url) throws Exception {
        return this.mockMvc.perform(get(url).header("Authorization", this.userTestHelper.getBearer()))
                .andDo(print()).andExpect(status().isOk());
    }

    public ResultActions sendRequestPost(String url, String content, String expectedResponseContent) throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post(url)
                .content(content)
                .contentType("application/json")
                .header("Authorization", this.userTestHelper.getBearer()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        if (expectedResponseContent != null) {
            resultActions.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedResponseContent)));
        }

        return resultActions;
    }

    public Flashcard getFlashcard(String textLeft, String textRight) {
        Flashcard flashcard = new Flashcard();
        flashcard.setKnowledgeType(Flashcard.TYPE_KNOW);
        flashcard.setTextLeft("Text left");
        flashcard.setTextRight("Test right");

        if (textLeft != null) {
            flashcard.setTextLeft(textLeft);
        }

        if (textRight != null) {
            flashcard.setTextRight(textRight);
        }

        return flashcard;
    }

    public FlashcardGroup getFlashcardGroup(String name) {
        FlashcardGroup flashcardGroup = new FlashcardGroup();
        flashcardGroup.setName("Flashcard group 1");
        flashcardGroup.setOrderNumber(1);
        flashcardGroup.setUserId(this.userTestHelper.getTestUserOrCreate().getId());

        if (name != null) {
            flashcardGroup.setName(name);
        }

        return flashcardGroup;
    }

    public FlashcardGroup saveFlashcardGroupWithFlashcard(Integer numberOfFlashcard) {
        List<Flashcard> flashcardList = new ArrayList<>();
        if (numberOfFlashcard != null) {
            for (int i = 0; i < numberOfFlashcard; i++) {
                flashcardList.add(this.getFlashcard(null, null));
            }
        }

        FlashcardGroup flashcardGroup = this.getFlashcardGroup("Flashcard group");
        flashcardGroup.setUserId(this.userTestHelper.getTestUserOrCreate().getId());
        flashcardGroup.setFlashcardList(flashcardList);
        this.flashcardGroupRepository.save(flashcardGroup);

        return flashcardGroup;
    }
}
