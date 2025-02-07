package org.example;

import java.io.Serializable;

public class User implements Serializable {
    private String id; // email hash = id
    private String email;
    private String encryptedCNP;
    private String encryptedPassword;


    // constructors
    public User(){}

    public User(String id, String email,
                String encryptedCNP, String encryptedPassword) {
        this.id = id;
        this.email = email;
        this.encryptedCNP = encryptedCNP;
        this.encryptedPassword = encryptedPassword;
    }


    // getters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedCNP() {
        return encryptedCNP;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }


    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEncryptedCNP(String encryptedCNP) {
        this.encryptedCNP = encryptedCNP;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}


