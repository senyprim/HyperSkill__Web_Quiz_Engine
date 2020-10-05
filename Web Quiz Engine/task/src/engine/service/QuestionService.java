package engine.service;

import engine.model.Account;
import engine.model.Question;
import engine.model.QuizItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Component
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;

    public Collection<Question> getAllQuestions(){
        return (List<Question>)questionRepo.findAll();
    }

    public Question addQuestion(Question question){
        return questionRepo.save(question);
    }
    public void deleteQuestion(Question question){questionRepo.delete(question);}
    public void deleteQuestionById(int id){questionRepo.deleteById(id);}

    public Optional<Question> getQuestionById(int id){
        return questionRepo.findById(id);
    }

    public boolean checkAnswerNumber(int idQuestion,int[] answerNumber){
        Optional<Question> question = getQuestionById(idQuestion);
        if (question.isEmpty()) throw  new QuizItemNotFoundException(idQuestion);
        return question.get().checkAnswers(answerNumber);
    }
}
