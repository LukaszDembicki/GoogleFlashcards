package flashcardApplication.user.helper;

import com.google.gson.Gson;
import flashcardApplication.user.entity.User;
import flashcardApplication.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class UserTestHelper {

    static String userUsername = "testuser";
    static String userPassword = "testuser";

    String accessToken = "";
    User currentUser = null;
    UserRepository userRepository;
    MockMvc mockMvc;

    @Autowired
    public UserTestHelper(UserRepository userRepository, MockMvc mockMvc) {
        this.userRepository = userRepository;
        this.mockMvc = mockMvc;
    }

    public String getBearer() {
        return "Bearer " + this.getAccessToken();
    }

    public String getAccessToken() {
        if (!this.accessToken.isEmpty()) {
            return this.accessToken;
        }

        User currentUser = this.getTestUserOrCreate();
        String jsonUser = new Gson().toJson(currentUser);

        try {
            ResultActions result
                    = mockMvc.perform(post("/authenticate")
                    .content(jsonUser)
                    .contentType("application/json"))
                    .andExpect(status().isOk());

            this.accessToken = result.andReturn().getResponse().getContentAsString();

            return this.accessToken;
        } catch (Exception unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return "";
        }
    }

    public User getTestUserOrCreate() {
        if (this.currentUser != null) {
            return this.currentUser;
        }

        Optional<User> user = this.userRepository.findByUsername(userUsername);

        if (user.isEmpty()) {
            this.currentUser = this.createTestUserIfDoNotExists();
        } else {
            this.currentUser = user.get();
        }

        return this.currentUser;
    }

    public User createTestUserIfDoNotExists() {
        return this.userRepository.save(this.getTestUserEntity());
    }

    private User getTestUserEntity() {
        User user = new User();
        user.setUsername(userUsername);
        user.setPassword(userPassword);
        user.setActive(User.USER_ACTIVE_TRUE);
        user.setRoles(User.ROLE_USER);

        return user;
    }
}
