package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Quiz {
    @JsonIgnore
    private int id;
    private String title;
    private String text;
    private String[] options;
    @JsonIgnore
    private int idAnswer;

    public Quiz(String title, String text, String[] options,int answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.idAnswer = answer;
    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int answer) {
        this.idAnswer = answer;
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

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public  boolean checkAnswer(int idAnswer){
        return idAnswer==getIdAnswer();
    }
}
