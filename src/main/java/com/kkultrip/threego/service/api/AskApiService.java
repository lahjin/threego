package com.kkultrip.threego.service.api;

import com.kkultrip.threego.model.Ask;
import com.kkultrip.threego.repository.api.AskApiRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AskApiService {

    private final AskApiRepo repo;

    public AskApiService(AskApiRepo repo) {
        this.repo = repo;
    }

    public Long save(Ask ask){
        ask = repo.save(ask);
        return ask.getId();
    }

    public int update(Ask ask){
        return repo.update(ask);
    }

    public int delete(Long id){
        return repo.delete(id);
    }

    public Optional<Ask> findById(Long id){
        return repo.findById(id);
    }

    public List<Ask> findByUserId(Long user_id){
        return repo.findByUserId(user_id);
    }

    public int countUserAsk(Long user_id){
        return repo.countByUserId(user_id);
    }
}
