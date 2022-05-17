package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewImage {
    private Long id;
    private Long review_id;
    private String original_name;
    private String name;
    private String path;
    private String size;
    private boolean active;
}
