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

}
