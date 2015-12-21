package com.valeriymiller.vktop.model;

import java.io.Serializable;

/**
 * Created by valer on 21.12.2015.
 */
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private String token;

    public Token(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Token() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}