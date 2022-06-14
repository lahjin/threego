package com.kkultrip.threego.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class PageDto {

    private int pageIndex;
    private String searchCondition;
    private String searchKeyword;

}
