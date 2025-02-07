package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionUtils {

    public String convertDateToString(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return date.format(formatter);
    }

    public LocalDateTime convertStringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return LocalDateTime.parse(date, formatter);
    }

    public int convertBoolenToInt(Boolean bool){
        return bool ? 1 : 0;
    }

    public Boolean convertIntToBoolean(int var){
        return var == 1 ? Boolean.TRUE : Boolean.FALSE;
    }
}
