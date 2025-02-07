package Controller;

import DtoModels.UserInfoDto;
import Utils.ConvertDtoToObject;
import Utils.ConvertObjectToDto;
import Utils.RestAPIException;
import Utils.RestAPIUtils;
import Utils.ServiceException;
import org.example.*;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.Vector;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserEndpoints {
    private final ServiceUser serviceUser;

    //constructor
    public UserEndpoints() {
        Properties serverProps = new Properties();
        RestAPIUtils.initialization(serverProps);
        UserRepository repoUser = new UserRepository(serverProps);
        serviceUser = new ServiceUser(repoUser);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Token login(@RequestBody SimpleUser simpleUser) throws RestAPIException {
        try {
            String id = serviceUser.login(simpleUser);
            if(id != null)
                return new Token(RestAPIUtils.getJWT(simpleUser.getEmail() + simpleUser.getEncryptedPassword()));
            else
                throw new RestAPIException("Cont gresit");
        } catch (ServiceException ex){
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Token register(@RequestBody User user) throws RestAPIException{
        try {
            String id = serviceUser.register(user);
            if(id != null)
                return new Token(RestAPIUtils.getJWT(user.getEmail() + user.getEncryptedPassword()));
            else
                throw new RestAPIException("cont gresit");
        } catch (ServiceException ex){
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/saveGeneralInformation", method = RequestMethod.POST)
    public Boolean saveGeneralInformation(@RequestBody UserInfoDto userInfoDto) throws RestAPIException{
        try {
            return serviceUser.saveGeneralInformation(ConvertDtoToObject.convertUserInfoDto(userInfoDto));
        } catch (ServiceException ex){
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Boolean resetPassword(@RequestBody User user) throws RestAPIException{
        try{
            return serviceUser.resetPassword(user);
        } catch (ServiceException ex){
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/getGeneralInformation", method = RequestMethod.GET)
    public UserInfoDto getGeneralInformation(@RequestParam String idUser) throws RestAPIException {
        try {
            return ConvertObjectToDto.convertUserInfo(serviceUser.getGeneralInformation(idUser));
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/getAllDonatorsOnDate", method = RequestMethod.GET)
    public Vector<SimpleUserInfo> getAllDonatorsOnDate(@RequestParam String date, @RequestParam String centerId) throws RestAPIException {
        try {
            return serviceUser.getAllDonatorsOnDate(date, centerId);
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }
}
