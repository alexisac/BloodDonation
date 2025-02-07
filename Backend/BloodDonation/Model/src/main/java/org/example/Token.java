package org.example;

import java.io.Serializable;

public class Token implements Serializable {
    private String token;

    //constructor
    public Token(){}
    public Token(String token) {
        this.token = token;
    }

    // getter
    public String getToken() {
        return token;
    }

    // setter
    public void setToken(String token) {
        this.token = token;
    }
}
