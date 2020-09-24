package service;

import model.Quiz;

import java.util.List;

public interface QuizService {

    void create(Quiz quiz);

    List<Quiz> readAll();

    Quiz read(int id);

    boolean update(Quiz quiz,int id);

    boolean delete(int id);

}
