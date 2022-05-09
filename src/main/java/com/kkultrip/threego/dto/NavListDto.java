package com.kkultrip.threego.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NavListDto {
    private String name;
    private String path;

    public NavListDto(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
