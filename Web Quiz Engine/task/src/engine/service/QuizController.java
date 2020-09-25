package engine.service;

import ch.qos.logback.core.net.server.Client;
import engine.model.Quiz;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping (value = "/quizzes")
    public ResponseEntity<Quiz> create(@RequestBody Quiz quiz){
        quizService.create(quiz);
        return new ResponseEntity<>(quiz,HttpStatus.OK);
    }

    @GetMapping (value = "/quizzes/{id}")
    public ResponseEntity<Quiz> read(@PathVariable(name="id") int id){
        final Quiz item = quizService.read(id);

        return  item!=null
                ?new ResponseEntity<>(item, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping (value="/quizzes")
    public ResponseEntity<List<Quiz>> read(){
        List<Quiz> items = quizService.readAll();

        return  items!=null
                ?new ResponseEntity<>(items, HttpStatus.OK)
                :new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
    }



    @PostMapping(value="/quizzes/{id}/solve")
    public ResponseEntity<String> checkAnswer(@PathVariable(name="id") int id,@RequestParam("answer") int idAnswer){
        final Quiz item = quizService.read(id);
        if (item==null)  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return  item.checkAnswer(idAnswer)
                ? new ResponseEntity<>(RESPONSE_RIGHT_ANSWER,HttpStatus.OK)
                : new ResponseEntity<>(RESPONSE_WRONG_ANSWER,HttpStatus.OK);
    }
}
