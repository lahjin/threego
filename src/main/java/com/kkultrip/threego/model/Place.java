package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@RequiredArgsConstructor
public class Place {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String latitude;
    @NotEmpty
    private String longitude;
    @NotEmpty
    private String address;
    @NotEmpty
    private String description;
    @NotEmpty
    private String guide;
    private String date;
    private boolean active;

}
