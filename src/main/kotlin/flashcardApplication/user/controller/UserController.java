package flashcardApplication.user.controller;

import com.google.gson.Gson;
import flashcardApplication.authentication.jwt.utils.JwtUtil;
import flashcardApplication.user.entity.User;
import flashcardApplication.user.model.AuthRequest;
import flashcardApplication.user.model.GoogleAuthRequest;
import flashcardApplication.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/user/google/login")
    @ResponseStatus(HttpStatus.OK)
    public String googleLogin(@RequestBody GoogleAuthRequest authRequest) throws Exception {
        try {
            User user = this.userService.registerOrGetUser(authRequest);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            return new Gson().toJson(jwtUtil.generateToken(user.getUsername()));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username or password!");
        }

        return jwtUtil.generateToken(authRequest.getUsername());
    }
}
