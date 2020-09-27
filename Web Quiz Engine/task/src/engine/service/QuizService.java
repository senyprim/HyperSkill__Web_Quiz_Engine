package engine.service;

import engine.model.Answers;
import engine.model.Quiz;
import engine.model.Result;

import java.util.List;

public interface QuizService {

    void create(Quiz quiz);

    Quiz[] read();

    Quiz read(int id);

    boolean update(Quiz quiz,int id);

    boolean delete(int id);

    Result checkAnswer(int id, Answers answers);

}
