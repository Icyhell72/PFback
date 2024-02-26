package fidness.parafarabi.farabiback.Service;

import fidness.parafarabi.farabiback.DTO.OtpRequest;
import fidness.parafarabi.farabiback.Entity.Status;
import fidness.parafarabi.farabiback.Entity.User;
import fidness.parafarabi.farabiback.Interface.UserInterface;
import fidness.parafarabi.farabiback.Repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterface {
    private final UserRepository userRepository;
    private final SmsService smsService;

    @Autowired
    public UserService(UserRepository userRepository, SmsService smsService) {
        this.userRepository = userRepository;
        this.smsService = smsService;
    }
    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User createUser(User user) {
        user.setUsername(user.getPhonenumber());
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty or blank");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setStatus(Status.NOT_VERIFIED);
        User createdUser = userRepository.save(user);
        OtpRequest otpRequest = new OtpRequest(user.getUsername(), user.getPhonenumber());
        smsService.sendSMS(otpRequest);
        return createdUser;
    }
    @Override
    public User updateUser(Integer id, User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty or blank");
        }
        if (userRepository.existsByUsername(user.getUsername()) &&
                !user.getUsername().equals(userRepository.findById(id).get().getUsername())) {
        }
        if (userRepository.existsById(id)) {return userRepository.save(user);}
        else {return null ;}
    }
    @Override
    public void deleteUser(Integer id) {userRepository.deleteById(id);}
}
