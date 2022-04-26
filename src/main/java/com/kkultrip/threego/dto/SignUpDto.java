package com.kkultrip.threego.dto;

import com.kkultrip.threego.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class SignUpDto {

    private String username;

    private String password;

    private String nickname;

    private Set<Role> roles;
}
