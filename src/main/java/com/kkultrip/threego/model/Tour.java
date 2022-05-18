package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Tour {

    private Long id;
    private String name;
    private String description;
    private String date;
    private boolean active;
    private Long category_id;

}
