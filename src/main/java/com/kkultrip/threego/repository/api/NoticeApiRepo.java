package com.kkultrip.threego.repository.api;

import com.kkultrip.threego.model.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeApiRepo {

    Optional<Notice> findById(Long id);
    int countAll();
    List<Notice> findAllPage(int startOffset, int indexSize);

}
