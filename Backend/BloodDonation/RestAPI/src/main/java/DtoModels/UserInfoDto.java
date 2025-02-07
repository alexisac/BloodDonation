package DtoModels;

import org.example.Enums.BloodType;
import org.example.Enums.Sex;
import org.example.UserInfo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfoDto implements Serializable {
    private String idUser;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String sex;
    private String bloodType;
    private int points;

    // constructor
    public UserInfoDto() {}

    public UserInfoDto(String idUser, String firstName, String lastName,
                       String birthDate, String sex,
                       String bloodType, int points) {
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

    public String getSex() {
        return sex;
    }

    public String getBloodType() {
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

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
