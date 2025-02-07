package Utils;

import DtoModels.UserInfoDto;
import org.example.Enums.BloodType;
import org.example.Enums.Sex;
import org.example.UserInfo;

public class ConvertDtoToObject {
    public static UserInfo convertUserInfoDto(UserInfoDto userInfoDto){
        return new UserInfo(
                userInfoDto.getIdUser(),
                userInfoDto.getFirstName(),
                userInfoDto.getLastName(),
                userInfoDto.getBirthDate(),
                Sex.getObject(userInfoDto.getSex()),
                BloodType.getObject(userInfoDto.getBloodType()),
                userInfoDto.getPoints()
        );
    }

}
