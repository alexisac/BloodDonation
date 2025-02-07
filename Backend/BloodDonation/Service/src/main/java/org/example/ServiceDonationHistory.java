package org.example;

import Utils.ServiceException;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public class ServiceDonationHistory {
    DonationHistoryRepository donationHistoryRepository;

    //constructor
    public ServiceDonationHistory(DonationHistoryRepository donationHistoryRepository) {
        this.donationHistoryRepository = donationHistoryRepository;
    }

    public Vector<DonationHistoryEntry> getAllDonationHistory(String idUser) throws ServiceException {
        try{
            Vector<DonationHistoryEntry> vect = donationHistoryRepository.getAllDonationHistory(idUser);
            return orderByDateDesc(vect);
        } catch (RepoException ex) {
            throw new ServiceException(ex.getMessageException());
        }
    }

    private Vector<DonationHistoryEntry> orderByDateDesc(Vector<DonationHistoryEntry> vect) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Collections.sort(vect, new Comparator<DonationHistoryEntry>() {
            @Override
            public int compare(DonationHistoryEntry o1, DonationHistoryEntry o2) {
                try {
                    Date date1 = sdf.parse(o1.getDonationDate());
                    Date date2 = sdf.parse(o2.getDonationDate());
                    return date2.compareTo(date1);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid date format");
                }
            }
        });
        return vect;
    }
}
