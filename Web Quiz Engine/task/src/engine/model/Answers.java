package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Answers {
    @JsonProperty("answer")
    private int[] answers;

    public Answers(int[] answers) {this.answers=answers;}

    public Answers(){}

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    public int[] getAnswers() {
        return answers==null?new int[0]:answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answers answers = (Answers) o;

        return Arrays.stream(this.getAnswers()).boxed().collect(Collectors.toSet())
                .equals(
               Arrays.stream(answers.getAnswers()).boxed().collect(Collectors.toSet())
                );
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(answers);
    }
}
