package engine.service;

import engine.model.Answers;
import engine.model.Quiz;
import engine.model.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiceImp implements QuizService{
    private final Map<Integer,Quiz> DATABASE = new HashMap<>();
    private final AtomicInteger ID = new AtomicInteger();

    @Override
    public void create(Quiz quiz) {
        final int id = ID.incrementAndGet();
        quiz.setId(id);
        DATABASE.put(quiz.getId(), quiz);
    }

    @Override
    public Quiz[] read() {
        return DATABASE.values().toArray(new Quiz[0]);
    }

    @Override
    public Quiz read(int id) {
        return DATABASE.get(id);
    }

    @Override
    public boolean update(Quiz quiz, int id) {
        if (!DATABASE.containsKey(id)) return false;
        quiz.setId(id);
        DATABASE.put(id,quiz);
        return true;
    }

    @Override
    public boolean delete(int id) {
        if (!DATABASE.containsKey(id)) return false;
        DATABASE.remove(id);
        return true;
    }

    @Override
    public Result checkAnswer(int id, Answers answers) {
        return new Result(read(id).checkAnswer(answers));
    }

}
