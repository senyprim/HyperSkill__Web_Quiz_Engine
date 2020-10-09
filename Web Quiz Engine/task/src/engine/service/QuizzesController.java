package engine.service;

import engine.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController

public class QuizzesController {
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
    private  final AccountService accountService;
    private final SolvedService solvedService;
    @Autowired
    public QuizzesController(QuestionService quizService,
                             AccountService accountService,
                             SolvedService solvedService){
        this.questionService = quizService;
        this.accountService = accountService;
        this.solvedService=solvedService;
    }

    public Account getCurrentAccount(Principal principal){
        return accountService.loadAccountByUsername(principal.getName());
    }

    //Создать аккаунт
    @PostMapping(value = "/api/register",consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody @Valid  Account account){
        boolean result = accountService.addAccount(account);
        return new ResponseEntity<>(result
                ?"Account "+account.getUsername()+" saved"
                :"Account "+account.getUsername()+" not saved"
                ,
                result
                        ?HttpStatus.OK
                        :HttpStatus.BAD_REQUEST
        );
    }
    //Выдать все вопросы
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping (value = "/api/quizzes")
    public Page<Question> read(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNo
            ,@RequestParam(name="size", defaultValue = "10") Integer pageSize
            ,@RequestParam(name="sort", defaultValue = "id") String sortBy)
    {
        return questionService.getAllQuestions(pageNo,pageSize,sortBy);
    }
    //Выдать все  решенния текущего пользователем
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping (value = "/api/quizzes/completed")
    public Page<Solved> readSolved(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNo
            ,@RequestParam(name="size", defaultValue = "10") Integer pageSize
            ,@RequestParam(name="sort", defaultValue = "solved") String sortBy
            ,Principal principal
    )
    {
        return solvedService.getAllSolved(pageNo,pageSize,sortBy,getCurrentAccount(principal));
    }
    //Выдать вопрос
    @GetMapping (value = "/api/quizzes/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Question read(@PathVariable(name="id") int id){
        return questionService.getQuestionById(id).orElseThrow(
                ()->new QuizItemNotFoundException(id));
    }
    //Добавить вопрос
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping (value = "/api/quizzes",consumes = "application/json")
    public Question create(@Valid @RequestBody Question question,Principal principal){
        Account currentAccount=getCurrentAccount(principal);
        currentAccount.addQuestion(question);
        return questionService.addQuestion(question);
    }
    //Ответить на вопрос
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(value="/api/quizzes/{id}/solve", consumes = "application/json")
    public Result checkAnswer(@PathVariable(name="id") int id
            ,@RequestBody RequestAnswer answer
            ,Principal principal){
        Optional<Question> question = questionService.getQuestionById(id);
        Account account = getCurrentAccount(principal);
        if (question.isEmpty()) throw  new QuizItemNotFoundException(id);
        boolean rightSAnswer =question.get().checkAnswers(answer.answer);
        if (rightSAnswer){
            solvedService.Solved(account,question.get());
        }
        return new Result(rightSAnswer);
    }
    //Удалить вопрос
    @DeleteMapping (value = "/api/quizzes/{id}")
    public ResponseEntity<Question> delete(@PathVariable(name="id") int id,Principal principal){
        Account currentAccount = getCurrentAccount(principal);
        Optional<Question> question = questionService.getQuestionById(id);

        if (question.isEmpty()) throw new QuizItemNotFoundException(id);
        if (!currentAccount.removeQuestion(question.get())) {
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        questionService.deleteQuestion(question.get());
        return new ResponseEntity<>(question.get(), HttpStatus.NO_CONTENT);
    }
}