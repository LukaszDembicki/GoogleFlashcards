package flashcardApplication.testPackage;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {

//    @Autowired
//    ApplicationEventPublisher applicationEventPublisher;
//
//    @Autowired
//    FooService fooService;

    @PostMapping("/api/test-endpoint/post")
    @ResponseStatus(HttpStatus.OK)
    public String responsePostFunction() {
        return "String";
    }

//    @GetMapping(value = "/api/test-endpoint")
//    @ResponseStatus(HttpStatus.OK)
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "/api/test-endpoint", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
//            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String testFunction(HttpServletResponse response) throws InterruptedException {
        Thread.sleep(2000);
        return new Gson().toJson("Response with header ");

    }
}
