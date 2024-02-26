package fidness.parafarabi.farabiback;

import com.twilio.Twilio;
import fidness.parafarabi.farabiback.Config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class FarabibackApplication {
    @Autowired
    private TwilioConfig twilioConfig;
    @PostConstruct
    public void setup() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }
    public static void main(String[] args) {
        SpringApplication.run(FarabibackApplication.class, args);
    }
}
