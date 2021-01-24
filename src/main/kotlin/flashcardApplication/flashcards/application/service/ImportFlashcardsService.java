package flashcardApplication.flashcards.application.service;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import flashcardApplication.flashcards.infrastructure.repository.FlashcardGroupRepository;
import flashcardApplication.user.service.UserService;
import org.openqa.selenium.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ImportFlashcardsService {

    private int FLASHCARD_LIST_LIMIT_SIZE = 10000;
    private int TRANSLATION_KEY_FROM = 2;
    private int TRANSLATION_KEY_TO = 3;

    private final UserService userService;
    private final FlashcardGroupRepository flashcardGroupRepository;

    @Autowired
    public ImportFlashcardsService(
            UserService userService,
            FlashcardGroupRepository flashcardGroupRepository
    ) {
        this.userService = userService;
        this.flashcardGroupRepository = flashcardGroupRepository;
    }

    public void importFlashcardsFromImport(MultipartFile multipartFile, String groupId) throws Exception {
        ArrayList<ArrayList<String>> flashcardListData = this.convertMultipartFileToArrayList(multipartFile);
        if (flashcardListData.size() >= FLASHCARD_LIST_LIMIT_SIZE) {
            throw new InvalidArgumentException(String.format("Flashcard list can't be greater that %d", FLASHCARD_LIST_LIMIT_SIZE));
        }
        FlashcardGroup currentFlashcardGroup = getCurrentFlashcardGroup(groupId);

        ArrayList<Flashcard> newFlashcardList = new ArrayList<Flashcard>();
        for (ArrayList<String> flashcardData : flashcardListData) {
            String flashcardLeft = flashcardData.get(this.TRANSLATION_KEY_FROM);
            String flashcardRight = flashcardData.get(this.TRANSLATION_KEY_TO);

            if (flashcardRight == null || flashcardLeft == null) {
                continue;
            }

            Flashcard flashcard = new Flashcard();
            flashcard.setCreatedAt(new Date());
            flashcard.setKnowledgeType(Flashcard.TYPE_UNASSIGNED);
            flashcard.setTextLeft(flashcardLeft);
            flashcard.setTextRight(flashcardRight);

            newFlashcardList.add(flashcard);
        }

        currentFlashcardGroup.setFlashcardList(newFlashcardList);
        flashcardGroupRepository.save(currentFlashcardGroup);
    }

    private FlashcardGroup getCurrentFlashcardGroup(String groupId) throws Exception {
        FlashcardGroup flashcardGroup = this.flashcardGroupRepository.findByIdAndUserId(groupId, this.userService.getCurrentUserId());
        if (flashcardGroup == null) {
            throw new Exception("Flashcard group has to be created. You can assign flashcards to existing group only.");
        }

        return flashcardGroup;
    }

    private ArrayList<ArrayList<String>> convertMultipartFileToArrayList(MultipartFile multipartFile) throws IOException {
        String originalFileName;
        if (Objects.equals(multipartFile.getOriginalFilename(), "")) {
            originalFileName = "file";
        } else {
            originalFileName = multipartFile.getOriginalFilename();
        }

        File file = new File(originalFileName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }

        SpreadSheet spread = new SpreadSheet(file);

        List<Sheet> sheets = spread.getSheets();
        Sheet sheet = sheets.get(0);
        Range range = sheet.getDataRange();
        Object values = range.getValues();
        String jsonData = new Gson().toJson(values);

        return new Gson().fromJson(jsonData, new TypeToken<ArrayList<ArrayList<String>>>() {
        }.getType());
    }
}
