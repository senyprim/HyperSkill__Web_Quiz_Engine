package engine.service;

import engine.model.Account;
import engine.model.Question;
import engine.model.QuizItemNotFoundException;
import engine.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public QuizzesController(QuestionService quizService, AccountService accountService){
        this.questionService = quizService;
        this.accountService = accountService;
    }

    public Account getCurrentAccount(){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountService.loadAccountByUsername(currentUser.getUsername());
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
    public Collection<Question> read(){
        return questionService.getAllQuestions();
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
    public Question create(@Valid @RequestBody Question question){
        getCurrentAccount().addQuestion(question);
        return questionService.addQuestion(question);
    }
    //Ответить на вопрос
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(value="/api/quizzes/{id}/solve", consumes = "application/json")
    public Result checkAnswer(@PathVariable(name="id") int id,@RequestBody RequestAnswer answer){
        return  new Result(questionService.checkAnswerNumber(id,answer.answer));
    }
    //Удалить вопрос
    @DeleteMapping (value = "/api/quizzes/{id}")
    public ResponseEntity<Question> delete(@PathVariable(name="id") int id){
        Account currentAccount = getCurrentAccount();
        Optional<Question> question = questionService.getQuestionById(id);

        if (question.isEmpty()) throw new QuizItemNotFoundException(id);
        if (!currentAccount.removeQuestion(question.get())) {
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        questionService.deleteQuestionById(id);
        return new ResponseEntity<>(question.get(), HttpStatus.NO_CONTENT);
    }
}