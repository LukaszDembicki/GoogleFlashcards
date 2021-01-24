package flashcardApplication.flashcards.application.controller;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import flashcardApplication.SanityBootTest;
import flashcardApplication.flashcards.helper.FlashcardsTestHelper;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import flashcardApplication.flashcards.infrastructure.repository.FlashcardGroupRepository;
import flashcardApplication.user.helper.UserTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImportsControllerTest extends SanityBootTest {
    private static final String SAMPLE_FILE_NAME = "src/test/flashcardApplication/flashcards/application/controller/files/Saved_translations.ods";

    UserTestHelper userTestHelper;
    FlashcardsTestHelper flashcardsTestHelper;
    FlashcardGroupRepository flashcardGroupRepository;

    @Autowired
    public ImportsControllerTest(
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
    public void testImportFileController() throws Exception {
        FlashcardGroup flashcardGroup = this.flashcardsTestHelper.getFlashcardGroup("Test flashcard group");
        this.flashcardGroupRepository.save(flashcardGroup);

        try {
            List<Sheet> sheets = new SpreadSheet(new File(SAMPLE_FILE_NAME)).getSheets();
            int numberOfRows = sheets.get(0).getDataRange().getValues().length;

            byte[] fileBytes = Files.readAllBytes(Paths.get(SAMPLE_FILE_NAME));
            MockMultipartFile jsonFile = new MockMultipartFile(
                    "Saved_translations.ods", "Saved_translations.ods",
                    "multipart/form-data",
                    fileBytes
            );

            mockMvc.perform(MockMvcRequestBuilders.multipart(String.format("/api/flashcards/%s/import/file", flashcardGroup.getId()))
                    .file("file", fileBytes)
                    .characterEncoding("UTF-8").header("Authorization", this.userTestHelper.getBearer()))
                    .andExpect(status().is2xxSuccessful());


            FlashcardGroup databaseFlashcardGroup = this.flashcardGroupRepository.findByIdAndUserId(
                    flashcardGroup.getId(),
                    this.userTestHelper.getTestUserOrCreate().getId()
            );

            Assertions.assertEquals(numberOfRows, databaseFlashcardGroup.getFlashcardList().size());

            this.flashcardGroupRepository.deleteById(flashcardGroup.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
