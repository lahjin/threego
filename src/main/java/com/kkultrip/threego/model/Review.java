package com.kkultrip.threego.model;

import lombok.*;

import java.util.Date;

@Getter @Setter
@RequiredArgsConstructor
public class Review {
        private Long id;
        private Long user_id;
        private Long tour_id;
        private String title;
        private String content;
        private String date;
        private int count;
        private String point;
        private boolean active;
        private int good;
        private int bad;
}
