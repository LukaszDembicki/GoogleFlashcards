package flashcardApplication.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Column;
import javax.persistence.Id;

@Document
public class User {
    public static String ROLE_USER = "USER";

    public static boolean USER_ACTIVE_TRUE = true;
    public static boolean USER_ACTIVE_FALSE = false;

    @Id()
    @Column(name = "user_id")
    private String id;

    @Column
    private String username;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    private boolean active;

    @Column
    private String roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        // todo return list
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
