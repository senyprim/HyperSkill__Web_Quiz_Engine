package engine.service;

import engine.model.Account;
import engine.model.Question;
import engine.model.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SolvedService {
    private SolvedRepo solvedRepo;
    public SolvedService(SolvedRepo solvedRepo){
        this.solvedRepo=solvedRepo;
    }
    public void Solved(Account account, Question question){
        solvedRepo.save(new Solved(account, question, new Date()));
    }
    public Page<Solved> getAllSolved(Integer pageNo, Integer pageSize, String sortBy,Account account){
        Pageable paging = PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
        Integer id = account.getId();
        Page<Solved> pageQuestion = solvedRepo.findByAccountId(id,paging);
        return pageQuestion;
    }
}
