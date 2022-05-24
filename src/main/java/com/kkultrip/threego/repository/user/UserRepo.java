package com.kkultrip.threego.repository.user;

import com.kkultrip.threego.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {

    List<User> findAll();
    int countAll();

    List<User> findAllPage(int startOffset, int indexSize);

    int countByUsername(String username);

    int countByNickname(String nickname);

    List<User> findByUsernamePage(String title, int startOffset, int indexSize);

    List<User> findByNicknamePage(String title, int startOffset, int indexSize);
    int countRecently();
}
