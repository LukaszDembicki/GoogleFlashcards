package flashcardApplication.testPackage.mongodb;

import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.infrastructure.repository.FlashcardGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoController {

    private Service service;
    private FlashcardGroupRepository flashcardGroupRepository;

    @Autowired
    public MongoController(
            Service service,
            FlashcardGroupRepository flashcardGroupRepository
    ) {
        this.service = service;
        this.flashcardGroupRepository = flashcardGroupRepository;
    }

    @GetMapping("/api/mongo")
    public void mongoAction() {

        flashcardGroupRepository.updateFlashcardKnowledgeTypeById(
                "60045f40460b127bc4cdaf82",
                Flashcard.TYPE_DONT_KNOW,
                "600313249ce2107fc2cc1b75"
        );


//        service.findByFlashcardId("60045f0a460b127bc4cdaca2");
//        service.updateTextRight("60045f40460b127bc4cdaf84", "new Text right test");


    }
}
