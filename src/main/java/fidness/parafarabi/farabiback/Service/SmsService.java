package fidness.parafarabi.farabiback.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import fidness.parafarabi.farabiback.Config.TwilioConfig;
import fidness.parafarabi.farabiback.DTO.OtpRequest;
import fidness.parafarabi.farabiback.DTO.OtpResponseDto;
import fidness.parafarabi.farabiback.DTO.OtpStatus;
import fidness.parafarabi.farabiback.DTO.OtpValidationRequest;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {
    private final TwilioConfig twilioConfig;
    Map<String, String> otpMap = new HashMap<>();
    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }
    public OtpResponseDto sendSMS(OtpRequest otpRequest) {
        OtpResponseDto otpResponseDto = null;
        try {
            PhoneNumber to =  new PhoneNumber(otpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String otp = generateOTP();
            String otpMessage = "Bienvenue sur Para El Farabi!, votre code est: " + otp + " .";
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
            otpMap.put(otpRequest.getUsername(), otp);
            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }
    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        String username = null;
        for(String key : keys)
            username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }
    private String generateOTP() {
        return new DecimalFormat("0000")
                .format(new Random().nextInt(9999));
    }
}
