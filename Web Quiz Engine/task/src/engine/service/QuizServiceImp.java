package engine.service;

import engine.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiceImp implements QuizService{
    private static final Map<Integer,Quiz> DATABASE = new HashMap<>();
    private static final AtomicInteger ID = new AtomicInteger();

    @Override
    public void create(Quiz quiz) {
        final int id = ID.incrementAndGet();
        quiz.setId(id);
        DATABASE.put(quiz.getId(), quiz);
    }

    @Override
    public List<Quiz> readAll() {
        return new ArrayList<>(DATABASE.values());
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
    public boolean checkAnswer(int id, int idAnswer) {
        return read(id).checkAnswer(idAnswer);
    }


    public QuizServiceImp() {
//        Quiz quiz = new Quiz("The Java Logo",
//                "What is depicted on the Java logo?",
//                new String[]{"Robot","Tea leaf","Cup of coffee","Bug"},
//                2);
//        this.create(quiz);
    }

}
