package com.spice.smsotpms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthToken {

    private String token;

    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
    }
}