package engine.service;

import engine.model.Question;
import engine.model.QuizItemNotFoundException;
import engine.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuestionController {
    private static class RequestAnswer{
        int[] answer;

        public RequestAnswer() {
        }

        public int[] getAnswer() {
            return answer;
        }

        public void setAnswer(int[] answer) {
            this.answer = answer;
        }
    }

    private  final QuestionService questionService;
    @Autowired
    public QuestionController(QuestionService quizService){
        this.questionService = quizService;
    }

    @PostMapping (consumes = "application/json")
    public ResponseEntity<Question> create(@Valid @RequestBody Question question){
        return new ResponseEntity<>(questionService.addQuestion(question),HttpStatus.OK);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<Question> read(@PathVariable(name="id") int id){
        final Optional<Question> item = questionService.getQuestionById(id);
        if (item.isEmpty()) throw new QuizItemNotFoundException(id);
        return new ResponseEntity<>(item.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Question[]> read(){
        return new ResponseEntity<Question[]>(questionService.getAllQuestions().toArray(new Question[0]), HttpStatus.OK);
    }

    @PostMapping(value="/{id}/solve", consumes = "application/json")
    public ResponseEntity<Result> checkAnswer(@PathVariable(name="id") int id,@RequestBody RequestAnswer answer){
        return  new ResponseEntity<>(new Result(questionService.checkAnswerNumber(id,answer.answer)),HttpStatus.OK);
    }
}
