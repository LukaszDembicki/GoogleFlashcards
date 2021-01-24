package flashcardApplication.testPackage;

import org.springframework.context.ApplicationEvent;

public class TestEvent extends ApplicationEvent {

    private String message;

    public TestEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
