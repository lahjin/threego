package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@RequiredArgsConstructor
public class Category {

    private Long id;
    @NotNull @NotEmpty
    private String name;
}
