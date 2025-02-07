package org.example;

import java.io.Serializable;

public class SimpleUser implements Serializable {
    private String email;
    private String encryptedPassword;

    // constructor
    public SimpleUser(){}
    public SimpleUser(String email, String encryptedPassword) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    // getters
    public String getEmail() {
        return email;
    }
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    // setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
