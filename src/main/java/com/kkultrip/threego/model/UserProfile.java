package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class UserProfile {

    private Long id;
    private String original_name;
    private String name;
    private String path;
    private String size;

}
