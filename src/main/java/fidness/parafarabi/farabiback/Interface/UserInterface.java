package fidness.parafarabi.farabiback.Interface;

import fidness.parafarabi.farabiback.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
    List<User> getAllUsers();

    Optional<User> getUserById(Integer id);
    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
