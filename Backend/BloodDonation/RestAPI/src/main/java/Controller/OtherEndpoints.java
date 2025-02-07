package Controller;

import DtoModels.DonationHistoryEntryDto;
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
public class OtherEndpoints {

    private final ServiceDonationCenter serviceDonationCenter;
    private final ServiceDonationHistory serviceDonationHistory;
    private final ServiceVerifyEligibility serviceVerifyEligibility;

    //constructor
    public OtherEndpoints() {
        Properties serverProps = new Properties();
        RestAPIUtils.initialization(serverProps);

        DonationCenterRepository donationCenterRepository = new DonationCenterRepository(serverProps);
        serviceDonationCenter = new ServiceDonationCenter(donationCenterRepository);

        DonationHistoryRepository donationHistoryRepository = new DonationHistoryRepository(serverProps);
        serviceDonationHistory = new ServiceDonationHistory(donationHistoryRepository);

        VerifyEligibilityRepository verifyEligibilityRepository = new VerifyEligibilityRepository(serverProps);
        serviceVerifyEligibility = new ServiceVerifyEligibility(verifyEligibilityRepository);
    }

    @RequestMapping(value = "/donationCenter/getAll", method = RequestMethod.GET)
    public Vector<DonationCenter> getAllDonationCenters() throws RestAPIException {
        try {
            return serviceDonationCenter.getAllDonationCenter();
        } catch (ServiceException ex){
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/donationHistory/getAll", method = RequestMethod.GET)
    public Vector<DonationHistoryEntryDto> getAllDonationHistory(@RequestParam String idUser) throws RestAPIException {
        try {
            Vector<DonationHistoryEntryDto> rez = new Vector<>();
            Vector<DonationHistoryEntry> vect = serviceDonationHistory.getAllDonationHistory(idUser);
            for (DonationHistoryEntry dhe : vect) {
                rez.add(ConvertObjectToDto.convertDonationHistoryEntryToDto(dhe));
            }
            return rez;
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/verifyEligibility/getEligibilityTypeForUser", method = RequestMethod.GET)
    public int getEligibilityTypeForUser(@RequestParam String idUser) throws RestAPIException{
        try{
            return serviceVerifyEligibility.getEligibilityTypeForUser(idUser);
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/verifyEligibility/saveEligibilityType", method = RequestMethod.PUT)
    public void saveEligibilityType(@RequestParam int eligibilityType, @RequestParam String idUser) throws RestAPIException{
        try{
            serviceVerifyEligibility.saveEligibilityType(idUser, eligibilityType);
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/bookedCenters/getBookedDates", method = RequestMethod.POST)
    public Vector<String> getBookedDates(@RequestParam String date, @RequestParam String centerId) throws RestAPIException{
        try {
            return serviceDonationCenter.getDates(date, centerId);
        } catch (ServiceException ex){
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/bookedCenters/bookDate", method = RequestMethod.PUT)
    public void saveScheduleDate(@RequestParam String idUser, @RequestParam String date, @RequestParam String centerId) throws RestAPIException{
        try{
            serviceDonationCenter.saveScheduleDate(idUser, date, centerId);
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }

    @RequestMapping(value = "/bookedCenters/getBookedForUser", method = RequestMethod.GET)
    public Vector<ScheduleCenter> getBookedForUser(@RequestParam String idUser) throws RestAPIException{
        try{
            return serviceDonationCenter.getBookedForUser(idUser);
        } catch (ServiceException ex) {
            throw new RestAPIException(ex.getMessageException());
        }
    }
}
