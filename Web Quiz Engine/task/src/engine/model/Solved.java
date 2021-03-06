package engine.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value = "completedAt")
    private Date solved;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    @JsonGetter("id")
    public int getQuestionId(){
        return question.getId();
    }

    public Solved(Account account,Question question,Date date){
        this.account=account;
        this.question=question;
        this.solved=date;
    }
}
