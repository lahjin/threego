package com.kkultrip.threego.service.loginlog;

import com.kkultrip.threego.repository.loginlog.LoginLogRepo;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService {
    private final LoginLogRepo loginLogRepo;

    public LoginLogService(LoginLogRepo loginLogRepo) {
        this.loginLogRepo = loginLogRepo;
    }

    public int countVisitor() {
        return loginLogRepo.countVisitor();
    }
}
