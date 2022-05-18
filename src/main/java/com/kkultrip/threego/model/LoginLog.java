package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class LoginLog {

    private Long id;
    private Long user_id;
    private String ip;
    private String date;
}
