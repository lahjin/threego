package com.kkultrip.threego.repository.notice;

import com.kkultrip.threego.model.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepo {
    Notice save(Notice notice);
    List<Notice> findAll();
    Optional<Notice> findById(Long id);
    List<Notice> findByTitle(String title);
    List<Notice> findByActive(boolean active);
    int updateById(Notice notice);
    int updateActive(Long id, boolean active);
    int countAll();
    List<Notice> findAllPage(int startOffset, int indexSize);
    int countByTitle(String title);
    List<Notice> findByTitlePage(String title, int startOffset, int indexSize);
}
