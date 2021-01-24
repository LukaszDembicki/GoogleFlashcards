package flashcardApplication.flashcards.infrastructure.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document
public class FlashcardGroup {

    @Id()
    private String id;

    private String name;

    private int orderNumber;

    private String userId;

//    @DBRef
    private List<Flashcard> flashcardList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Flashcard> getFlashcardList() {
        return flashcardList;
    }

    public void setFlashcardList(List<Flashcard> flashcardList) {
        this.flashcardList = flashcardList;
    }

    public void addFlashcard(Flashcard flashcard) {
        this.flashcardList.add(flashcard);
    }
}
