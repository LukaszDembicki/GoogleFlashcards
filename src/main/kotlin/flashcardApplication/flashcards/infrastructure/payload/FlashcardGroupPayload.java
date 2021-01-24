package flashcardApplication.flashcards.infrastructure.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FlashcardGroupPayload {

    private Long id;

    @NotBlank
    @Size(min = 1, message = "")
    private String name;

    @NotBlank
    @Size(min = 1)
    private Integer orderNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
