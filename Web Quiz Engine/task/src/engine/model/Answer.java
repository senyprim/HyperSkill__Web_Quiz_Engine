package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;
    private String answer;
    @JsonIgnore
    private int ansNumber;
    @JsonIgnore
    private boolean rightAns;

    public Answer() {
    }

    public Answer(String answer, int number, boolean right) {
        this.answer=answer;
        this.ansNumber =number;
        this.rightAns = right;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnsNumber() {
        return ansNumber;
    }

    public void setAnsNumber(int ansNumber) {
        this.ansNumber = ansNumber;
    }

    public boolean isRightAns() {
        return rightAns;
    }

    public void setRightAns(boolean rightAns) {
        this.rightAns = rightAns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
