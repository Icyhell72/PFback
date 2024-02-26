package fidness.parafarabi.farabiback.Entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import java.util.Date;
import org.springframework.lang.Nullable;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Nullable
    private String name;
    @Nullable
    private String lastname;
    @NonNull
    @Column(unique = true, nullable = false)
    private String username;
    @Nullable
    private Date birthdate;
    @Nullable
    private String gender;
    @Nullable
    private String email;
    @NonNull
    private String phonenumber;
    @Nullable
    private String password;

    private Status status;
    @Nullable
    private String cardcode;
    @Nullable
    private String sponsorcode;
    @Nullable
    private String operatorcode;
    @Nullable
    private String profileImg;
}
