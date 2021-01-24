package flashcardApplication.flashcards.application.controller;

import flashcardApplication.flashcards.application.service.ImportFlashcardsService;
import org.openqa.selenium.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ImportController {

    private final ImportFlashcardsService importFlashcardsService;

    @Autowired
    ImportController(ImportFlashcardsService importFlashcardsService) {
        this.importFlashcardsService = importFlashcardsService;
    }

    @PostMapping(value = "/api/flashcards/{groupId}/import/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importFileController(@PathVariable String groupId, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        try {
            this.importFlashcardsService.importFlashcardsFromImport(multipartFile, groupId);
        } catch (InvalidArgumentException invalidArgumentException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidArgumentException.getMessage(), invalidArgumentException);
        }
    }
}
