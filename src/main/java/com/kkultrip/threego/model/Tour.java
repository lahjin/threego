package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@RequiredArgsConstructor
public class Tour {

    private Long id;
    @NotNull @Min(1)
    private String name;
    @NotNull @Min(1)
    private String description;
    private String date;
    private boolean active;
    private Long category_id;

}
