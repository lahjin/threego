package com.kkultrip.threego.repository.ask;

import com.kkultrip.threego.model.Ask;

import java.util.List;
import java.util.Optional;

public interface AskRepo {
    Ask save(Ask ask);
    List<Ask> findAll();
    Optional<Ask> findById(Long id);
    List<Ask> findByUserID(Long user_id);
    List<Ask> findByTitle(String title);
    List<Ask> findByActive(boolean active);
    int updateById(Ask ask);
    int updateActive(Long id, boolean active);
    int countAll();
    List<Ask> findAllPage(int startOffset, int indexSize);
    int countByTitle(String title);
    List<Ask> findByTitlePage(String title, int startOffset, int indexSize);
    int countByUserID(String user_id);
    List<Ask> findByUserIDPage(String user_id, int startOffset, int indexSize);
}
