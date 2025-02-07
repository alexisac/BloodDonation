package org.example;

import Utils.ConversionUtils;
import Utils.ServiceException;

import java.util.ResourceBundle;
import java.util.Vector;

public class ServiceDonationCenter {
    DonationCenterRepository donationCenterRepository;

    //constructor
    public ServiceDonationCenter(DonationCenterRepository donationCenterRepository) {
        this.donationCenterRepository = donationCenterRepository;
    }

    public Vector<DonationCenter> getAllDonationCenter() throws ServiceException {
        try {
            return donationCenterRepository.getAllCenters();
        } catch (RepoException ex){
            throw new ServiceException(ex.getMessageException());
        }
    }

    public Vector<String> getDates(String date, String centerId) throws ServiceException {
        try{
            return donationCenterRepository.getDates(date, centerId);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }

    public void saveScheduleDate(String idUser, String date, String centerId) throws ServiceException {
        try{
            donationCenterRepository.saveScheduleDate(idUser, date, centerId);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }

    public Vector<ScheduleCenter> getBookedForUser(String idUser) throws ServiceException {
        try{
            return donationCenterRepository.getBookedForUser(idUser);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }
}
