package fidness.parafarabi.farabiback.Repository;

import fidness.parafarabi.farabiback.Entity.User;
import fidness.parafarabi.farabiback.Service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhonenumber(Integer phonenumber);


}