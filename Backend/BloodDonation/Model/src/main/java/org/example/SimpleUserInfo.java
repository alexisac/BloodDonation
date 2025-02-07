package org.example;

import java.io.Serializable;

public class SimpleUserInfo implements Serializable {
    private String idUser;
    private String firstName;
    private String lastName;

    // constructor
    public SimpleUserInfo(String idUser, String firstName, String lastName) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters
    public String getIdUser() {
        return idUser;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    // setters
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
