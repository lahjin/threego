package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@RequiredArgsConstructor
public class Ask {
    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private String date;
    @NotNull @NotEmpty
    private String answer;
    private boolean active;
}
