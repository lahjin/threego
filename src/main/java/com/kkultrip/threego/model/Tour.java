package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@RequiredArgsConstructor
public class Tour {

    private Long id;
    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String description;
    private String date;
    private boolean active;
    private Long category_id;

}
