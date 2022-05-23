package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter @Setter
@RequiredArgsConstructor
public class Notice {
    private Long id;
    @NotNull @Min(1)
    private String title;
    @NotNull @Min(1)
    private String content;
    private String date;
    private boolean active;
}
