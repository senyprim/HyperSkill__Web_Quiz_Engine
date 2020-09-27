package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Quiz {
    private int id;

    @NotBlank(message = "Title should not be empty")
    private String title;

    @NotBlank(message = "Text should not be empty")
    private String text;

    @NotNull
    @Size(min=2, message ="Options should  be minimum 2 item")
    private List<String> options;

    @JsonProperty(value="answer",access = JsonProperty.Access.WRITE_ONLY)
    private Answers answers;

    public Quiz() {}

    public Answers getAnswers() {
        return this.answers==null?new Answers():this.answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = new Answers(answers);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public  boolean checkAnswer(Answers answers){
        return this.getAnswers().equals(answers);
    }
}
