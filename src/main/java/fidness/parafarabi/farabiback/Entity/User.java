package fidness.parafarabi.farabiback.Entity;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastname;
    private Date birthdate;
    private String gender;
    private String email;
    private Integer phonenumber;
    private String password;
    @Nullable
    private String cardcode;
    @Nullable
    private String sponsorcode;
    @Nullable
    private String operatorcode;
    @Nullable
    private String profileImg;

    public String PhoneNumberToString(Integer phonenumber) {
        // Check if the phone number is null
        if (phonenumber == null) {
            return null;
        }
        return phonenumber.toString();
    }

    public Integer stringToPhoneNumber(String phoneNumberString) {
        if (phoneNumberString == null || phoneNumberString.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(phoneNumberString);
        } catch (NumberFormatException e) {
            return null;
        }
    }



}
