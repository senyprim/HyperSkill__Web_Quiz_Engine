package engine.service;

import engine.model.Quiz;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuizController {
    private final String RESPONSE_RIGHT_ANSWER="{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
    private final String RESPONSE_WRONG_ANSWER="{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";


    private class QuizAnswer{

    }
    private  final QuizService quizService;
    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }
    @GetMapping (value="/quiz")
    public ResponseEntity<Quiz> read(){
        final Quiz item = quizService.readAll().stream().findAny().orElse(null);

        return  item!=null
                ?new ResponseEntity<>(item, HttpStatus.OK)
                :new ResponseEntity<>(item, HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="/quiz")
    public ResponseEntity<String> checkAnswer(@RequestParam("answer") int idAnswer){
        final Quiz item = quizService.readAll().stream().findAny().orElse(null);
        if (item==null)  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return  item.checkAnswer(idAnswer)
                ? new ResponseEntity<>(RESPONSE_RIGHT_ANSWER,HttpStatus.OK)
                : new ResponseEntity<>(RESPONSE_WRONG_ANSWER,HttpStatus.OK);
    }

}
