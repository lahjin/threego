package com.kkultrip.threego.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class ReviewDto {
        private Long id;
        private Long user_id;
        private String nickname;
        private Long tour_id;
        private String tour_name;
        private String title;
        private String content;
        private String date;
        private int count;
        private String point;
        private boolean active;
        private int good;
        private int bad;

        private String image_name1;
        private String image_name2;
        private String image_name3;

        @Builder

        public ReviewDto(Long id, Long user_id, String nickname, Long tour_id, String tour_name, String title, String content, String date, int count, String point, boolean active, int good, int bad, String image_name1, String image_name2, String image_name3) {
                this.id = id;
                this.user_id = user_id;
                this.nickname = nickname;
                this.tour_id = tour_id;
                this.tour_name = tour_name;
                this.title = title;
                this.content = content;
                this.date = date;
                this.count = count;
                this.point = point;
                this.active = active;
                this.good = good;
                this.bad = bad;
                this.image_name1 = image_name1;
                this.image_name2 = image_name2;
                this.image_name3 = image_name3;
        }
}
