package Utils;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Validation {

    public boolean emailValidation(String email){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public boolean nameValidation(String name){
        return name.matches("^[a-zA-Z][a-zA-Z -]+[a-zA-Z]$");
    }

    public boolean locationValidation(String location){
        return true;
    }

    public boolean birthDateValidation(LocalDateTime birthDate){
        return ChronoUnit.YEARS.between(birthDate, LocalDateTime.now()) >= Constants.MIN_AGE_FOR_DONATE;
    }

    public boolean lastDonateValidation(LocalDateTime lastDonation){
        return ChronoUnit.DAYS.between(lastDonation, LocalDateTime.now()) >= Constants.MIN_TIME_FOR_LAST_DONATION;
    }

    public boolean dateValidation(LocalDateTime date){
        return date.isBefore(LocalDateTime.now());
    }
}
