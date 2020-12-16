package bugtracker.exceptions;

public class NameIsNotCorrectException extends Exception {
    public NameIsNotCorrectException(String description) {
        super(description);
    }
}
