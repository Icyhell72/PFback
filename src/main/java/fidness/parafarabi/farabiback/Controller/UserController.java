package fidness.parafarabi.farabiback.Controller;

import fidness.parafarabi.farabiback.DTO.OtpValidationRequest;
import fidness.parafarabi.farabiback.Entity.Status;
import fidness.parafarabi.farabiback.Entity.User;
import fidness.parafarabi.farabiback.Service.UserService;
import fidness.parafarabi.farabiback.Service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final SmsService smsService;
    @Autowired
    public UserController(UserService userService, SmsService smsService) {
        this.userService = userService;
        this.smsService = smsService;
    }
    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createduser = userService.createUser(user);
        return new ResponseEntity<>(createduser, HttpStatus.CREATED);
    }
    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        try {
            String result = smsService.validateOtp(otpValidationRequest);
            if (result.equals("OTP is valid!")) {
                String username = otpValidationRequest.getUsername();
                User user = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                user.setStatus(Status.VERIFIED);
                userService.updateUser(user.getId(), user);
                return ResponseEntity.ok("OTP is valid and user is verified");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP is invalid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User newUser) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(newUser.getName());
            existingUser.setLastname(newUser.getLastname());
            existingUser.setBirthdate(newUser.getBirthdate());
            existingUser.setGender(newUser.getGender());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPassword(newUser.getPassword());
            User updatedUser = userService.updateUser(id, existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
