package org.example;

import Utils.ServiceException;

public class ServiceVerifyEligibility {
    VerifyEligibilityRepository verifyEligibilityRepository;

    // constructor
    public ServiceVerifyEligibility(VerifyEligibilityRepository verifyEligibilityRepository) {
        this.verifyEligibilityRepository = verifyEligibilityRepository;
    }

    public int getEligibilityTypeForUser(String idUser) throws ServiceException {
        try{
            return verifyEligibilityRepository.getEligibilityTypeForUser(idUser);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }

    public void saveEligibilityType(String idUser, int typeValue) throws ServiceException {
        try{
            verifyEligibilityRepository.saveEligibilityType(idUser, typeValue);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }
}
