package fidness.parafarabi.farabiback.Exception;

// A custom exception class for invalid user input
public class InvalidUserInputException extends CustomException {

    // A constructor that takes the error message as a parameter
    public InvalidUserInputException(String message) {
        super(message);
    }

    // A method to get the error message
    @Override
    public String getMessage() {
        // Return the error message with a prefix
        return "Invalid user input: " + super.getMessage();
    }
}
