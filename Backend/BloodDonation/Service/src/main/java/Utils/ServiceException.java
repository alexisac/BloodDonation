package Utils;

public class ServiceException extends Exception {
    public String messageException;

    // constructor
    public ServiceException(String myMessage) {
        super(myMessage);
        this.messageException = myMessage;
    }

    // getter for message
    public String getMessageException() {
        return messageException;
    }
}
