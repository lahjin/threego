package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@RequiredArgsConstructor
public class Category {

    private Long id;
    @NotNull @Min(1)
    private String name;
}
