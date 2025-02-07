package DtoModels;

import org.example.Enums.DonationType;

import java.io.Serializable;

public class DonationHistoryEntryDto implements Serializable {
    private String idUser;
    private String donationDate;
    private String donationType;

    // constructors
    public DonationHistoryEntryDto() {
    }
    public DonationHistoryEntryDto(String idUser, String donationDate, String donationType) {
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
    public String getDonationType() {
        return donationType;
    }

    // setters
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }
    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }
}
