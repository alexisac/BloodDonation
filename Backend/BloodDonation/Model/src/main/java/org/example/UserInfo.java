package org.example;

import org.example.Enums.BloodType;
import org.example.Enums.Sex;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfo implements Serializable {
    private String idUser;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Sex sex;
    private BloodType bloodType;
    private int points;

    // constructor
    public UserInfo() {}

    public UserInfo(String idUser, String firstName, String lastName,
                    String birthDate, Sex sex,
                    BloodType bloodType, int points) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.bloodType = bloodType;
        this.points = points;
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

    public String getBirthDate() {
        return birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public int getPoints() {
        return points;
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

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
