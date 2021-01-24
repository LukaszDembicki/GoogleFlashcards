package flashcardApplication;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {FlashcardsApplication.class})
public class SanityBootTest {
    protected MockMvc mockMvc;

    public SanityBootTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
}
