package engine.service;

import engine.model.Question;
import engine.model.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface SolvedRepo extends CrudRepository<Solved,Integer> {
    Page<Solved> findByAccountId(Integer id,Pageable paging);
}
