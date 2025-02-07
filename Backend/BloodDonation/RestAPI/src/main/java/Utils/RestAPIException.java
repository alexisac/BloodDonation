package Utils;

public class RestAPIException extends Exception {
    public String messageException;

    // constructor
    public RestAPIException(String myMessage) {
        super(myMessage);
        this.messageException = myMessage;
    }

    // getter for message
    public String getMessageException() {
        return messageException;
    }
}
