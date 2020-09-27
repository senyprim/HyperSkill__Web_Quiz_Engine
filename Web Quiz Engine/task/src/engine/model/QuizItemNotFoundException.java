package engine.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class QuizItemNotFoundException extends  RuntimeException{
    private int id;
    public QuizItemNotFoundException(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
