package engine.service;

import ch.qos.logback.core.net.server.Client;
import engine.model.Answers;
import engine.model.Quiz;
import engine.model.QuizItemNotFoundException;
import engine.model.Result;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private class QuizAnswer{

    }
    private  final QuizService quizService;
    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping (consumes = "application/json")
    public ResponseEntity<Quiz> create(@Valid @RequestBody Quiz quiz){
        quizService.create(quiz);
        return new ResponseEntity<>(quiz,HttpStatus.OK);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<Quiz> read(@PathVariable(name="id") int id){
        final Quiz item = quizService.read(id);
        if (item==null) throw new QuizItemNotFoundException(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Quiz[]> read(){
        return new ResponseEntity<>(quizService.read(), HttpStatus.OK);
    }

    @PostMapping(value="/{id}/solve")
    public ResponseEntity<Result> checkAnswer(@PathVariable(name="id") int id,@RequestBody Answers answers){
        final Quiz item = quizService.read(id);
        if (item==null)  throw  new QuizItemNotFoundException(id);
        return  new ResponseEntity<>(new Result(item.checkAnswer(answers)),HttpStatus.OK);
    }
}
