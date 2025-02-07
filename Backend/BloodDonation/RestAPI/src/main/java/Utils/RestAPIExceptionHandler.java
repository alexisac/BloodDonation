package Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestAPIExceptionHandler {

    @ExceptionHandler(RestAPIException.class)
    public ResponseEntity<String> handleRestAPIException(RestAPIException ex) {
        return new ResponseEntity<>(ex.getMessageException(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
