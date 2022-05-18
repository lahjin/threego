package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Place {

    private Long id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String description;
    private String guide;
    private String date;
    private boolean active;

}
