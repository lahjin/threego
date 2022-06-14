package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Ask;

import java.util.List;
import java.util.Optional;

public interface AskApiRepo {

    Ask save(Ask ask);
    Optional<Ask> findById(Long id);
    List<Ask> findByUserId(Long user_id);
    int update(Ask ask);
    int delete(Long id);
    int countByUserId(Long user_id);
}
