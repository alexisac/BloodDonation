package org.example;
import Utils.ConversionUtils;
import Utils.ServiceException;
import Utils.Validation;

import java.util.Vector;

public class ServiceUser {
    UserRepository repository;
    Validation validation = new Validation();
    ConversionUtils conversionUtils = new ConversionUtils();

    //constructor
    public ServiceUser(UserRepository repository) {
        this.repository = repository;
    }

    public String login(SimpleUser user) throws ServiceException{
        try{
            if(!validation.emailValidation(user.getEmail())) {
                throw new ServiceException("ServiceUser - login - email invalid");
            }
            return repository.login(user.getEmail(), user.getEncryptedPassword());
        } catch (RepoException ex){
            throw new ServiceException(ex.getMessageException());
        }
    }

    public String register(User user) throws ServiceException{
        try{
            if(!validation.emailValidation(user.getEmail())) {
                throw new ServiceException("ServiceUser - register - email invalid");
            }

            if(repository.searchAccount(user.getEmail(), user.getEncryptedCNP()) != null){
                throw new ServiceException("Another account with this email and CNP already exist");
            }

            return repository.register(user.getId(), user.getEmail(), user.getEncryptedCNP(), user.getEncryptedPassword());
        } catch (RepoException ex){
            throw new ServiceException(ex.getMessageException());
        }
    }

    public Boolean resetPassword(User user) throws ServiceException{
        try{
            if(!validation.emailValidation(user.getEmail())) {
                throw new ServiceException("ServiceUser - resetPassword - email invalid");
            }

            if(repository.searchAccount(user.getEmail(), user.getEncryptedCNP()) == null){
                throw new ServiceException("This account doesn't exist");
            }

            return repository.resetPassword(user.getId(), user.getEmail(), user.getEncryptedCNP(), user.getEncryptedPassword());
        } catch (RepoException ex){
            throw new ServiceException(ex.getMessageException());
        }
    }

    private String validateUserInformation(UserInfo userInfo) {
        String err = "";

        if(!validation.nameValidation(userInfo.getFirstName())){
            err += "FirstName is invalid. ";
        }

        if(!validation.nameValidation(userInfo.getLastName())){
            err += "LastName is invalid. ";
        }

        return err;
    }

    public Boolean saveGeneralInformation(UserInfo userInfo) throws ServiceException {
        try{
            String err = validateUserInformation(userInfo);
            if(err.length() > 0){
                throw new ServiceException(err);
            }

            repository.saveGeneralInformation(userInfo.getIdUser(), userInfo.getFirstName(), userInfo.getLastName(),
                    userInfo.getBirthDate(), userInfo.getSex().getIntValue(),
                    userInfo.getBloodType().getIntValue(), userInfo.getPoints());
            return true;
        } catch (RepoException ex){
            throw new ServiceException(ex.getMessageException());
        }
    }

    public UserInfo getGeneralInformation(String idUser) throws ServiceException {
        try{
            return repository.getGeneralInformation(idUser);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }

    public Vector<SimpleUserInfo> getAllDonatorsOnDate(String date, String centerId) throws ServiceException {
        try {
            return repository.getAllDonatorsOnDate(date, centerId);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }
}
