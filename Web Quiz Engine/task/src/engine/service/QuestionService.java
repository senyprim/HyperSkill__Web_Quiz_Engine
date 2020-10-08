package engine.service;

import engine.model.Account;
import engine.model.Question;
import engine.model.QuizItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.naming.ldap.PagedResultsResponseControl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Component
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;

    public Page<Question> getAllQuestions(
            Integer pageNo,Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Question> pageQuestion = questionRepo.findAll(paging);
        return pageQuestion;
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
