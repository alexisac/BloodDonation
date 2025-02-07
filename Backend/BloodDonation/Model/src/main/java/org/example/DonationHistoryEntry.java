package org.example;

import org.example.Enums.DonationType;

import java.io.Serializable;

public class DonationHistoryEntry implements Serializable {
    private String idUser;
    private String donationDate;
    private DonationType donationType;

    // constructors
    public DonationHistoryEntry() {
    }
    public DonationHistoryEntry(String idUser, String donationDate, DonationType donationType) {
        this.idUser = idUser;
        this.donationDate = donationDate;
        this.donationType = donationType;
    }

    // getters
    public String getIdUser() {
        return idUser;
    }
    public String getDonationDate() {
        return donationDate;
    }
    public DonationType getDonationType() {
        return donationType;
    }

    // setters
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }
    public void setDonationType(DonationType donationType) {
        this.donationType = donationType;
    }
}
