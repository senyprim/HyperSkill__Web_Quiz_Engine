package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
    private  final QuizService quizService;
    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }
}
