package engine.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity

public class Question {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Title should not be empty")
    private String title;

    @NotBlank(message = "Text should not be empty")
    private String text;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="questionId")
    @NotNull(message = "Size must be 2 and much")
    @Size(min = 2, message = "Size must be 2 and much")

    private List<Answer> answers;

    public Question() {}

    public void addAnswer(String text,int number){
        this.answers.add(new Answer(text,number,false));
    }

    public int getId() {
        return id;
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

    @JsonGetter("options")
    public String[] getJSonAnswer(){
        return answers==null?new String[0]
                :this.answers.stream().map(Answer::getAnswer).toArray(String[]::new);
    }
    public List<Answer> getAnswers() {
        return answers==null?new ArrayList<>():this.answers;
    }

    @JsonSetter("options")
    public void setAnswers(String[] answers){
        this.answers=new ArrayList<>();
        if (answers==null) return;
        for (int i=0;i<answers.length;i++)
            addAnswer(answers[i],i);
    }

    @JsonSetter("answer")
    public void setRight(int[] numbers){
        Set<Integer> sets = Arrays.stream(
                Optional.ofNullable(numbers)
                .orElse(new int[0]))
                .boxed()
                .collect(Collectors.toSet());
        for (Answer answer: getAnswers()) {
            answer.setRightAns(sets.contains(answer.getAnsNumber()));
        }
    }

    public boolean checkAnswers(int[] numbers){
        Set<Integer> setNumber = Arrays.stream(
                Optional.ofNullable(numbers)
                        .orElse(new int[0]))
                .boxed()
                .collect(Collectors.toSet());

        Set<Integer> setRightAnswer =this.answers
                .stream()
                .filter(Answer::isRightAns)
                .map(Answer::getAnsNumber)
                .collect(Collectors.toSet());

        return setNumber.equals(setRightAnswer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
