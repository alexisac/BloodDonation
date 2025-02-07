package Utils;

import DtoModels.DonationHistoryEntryDto;
import DtoModels.UserInfoDto;
import org.example.DonationHistoryEntry;
import org.example.Enums.BloodType;
import org.example.Enums.Sex;
import org.example.UserInfo;

public class ConvertObjectToDto {
    public static DonationHistoryEntryDto convertDonationHistoryEntryToDto(DonationHistoryEntry dhe){
        return new DonationHistoryEntryDto(
                dhe.getIdUser(),
                dhe.getDonationDate(),
                dhe.getDonationType().getName()
        );
    }

    public static UserInfoDto convertUserInfo(UserInfo userInfo){
        return new UserInfoDto(
                userInfo.getIdUser(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getBirthDate(),
                String.valueOf(userInfo.getSex().getIntValue()),
                String.valueOf(userInfo.getBloodType().getIntValue()),
                userInfo.getPoints()
        );
    }
}
