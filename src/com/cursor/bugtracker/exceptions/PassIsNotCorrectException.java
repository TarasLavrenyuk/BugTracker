package bugtracker.exceptions;

public class PassIsNotCorrectException extends Exception{
    public PassIsNotCorrectException(String description) {
        super(description);
    }
}
