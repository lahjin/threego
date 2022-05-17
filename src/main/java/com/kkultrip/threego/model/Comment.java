package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class Comment {
    private Long id;
    private Long review_id;
    private Long user_id;
    private String content;
    private String date;
    private boolean active;
}
