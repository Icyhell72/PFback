package fidness.parafarabi.farabiback.Exception;

// A base class for all custom exceptions
public class CustomException extends Exception {

    // A field to store the error message
    private String message;

    // A constructor that takes the error message as a parameter
    public CustomException(String message) {
        this.message = message;
    }

    // A method to get the error message
    public String getMessage() {
        return message;
    }
}
