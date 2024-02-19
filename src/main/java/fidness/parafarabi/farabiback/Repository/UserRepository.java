package fidness.parafarabi.farabiback.Repository;

import fidness.parafarabi.farabiback.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}