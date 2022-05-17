package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Ask {
    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private String date;
    private String answer;
    private boolean active;
}
