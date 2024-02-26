package fidness.parafarabi.farabiback.Exception;

// A custom exception class for SMS API errors
public class SmsApiException extends CustomException {

    // A constructor that takes the error message as a parameter
    public SmsApiException(String message) {
        super(message);
    }

    // A method to get the error message
    @Override
    public String getMessage() {
        // Return the error message with a prefix
        return "SMS API error: " + super.getMessage();
    }
}
