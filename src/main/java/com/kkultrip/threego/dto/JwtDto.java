package com.kkultrip.threego.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtDto {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String nickname;
    private List<String> roles;

    @Builder
    public JwtDto(String token, Long id, String username, String nickname, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }
}