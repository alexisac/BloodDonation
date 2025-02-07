package org.example;

public class RepoException extends Exception {
    public String messageException;

    // constructor
    public RepoException(String myMessage) {
        super(myMessage);
        this.messageException = myMessage;
    }

    // getter for message
    public String getMessageException() {
        return messageException;
    }
}
