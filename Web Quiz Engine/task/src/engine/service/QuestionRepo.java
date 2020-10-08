package engine.service;

import engine.model.Question;
import engine.model.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface QuestionRepo extends PagingAndSortingRepository<Question,Integer> {
}
