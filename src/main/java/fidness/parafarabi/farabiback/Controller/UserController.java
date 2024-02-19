package fidness.parafarabi.farabiback.Controller;

import fidness.parafarabi.farabiback.Entity.User;
import fidness.parafarabi.farabiback.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User newUser) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update the existing user with the new data
            existingUser.setName(newUser.getName());
            existingUser.setLastname(newUser.getLastname());
            existingUser.setBirthdate(newUser.getBirthdate());
            existingUser.setGender(newUser.getGender());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPhonenumber(newUser.getPhonenumber());
            existingUser.setPassword(newUser.getPassword());
            existingUser.setCardcode(newUser.getCardcode());
            existingUser.setSponsorcode(newUser.getSponsorcode());
            existingUser.setOperatorcode(newUser.getOperatorcode());
            existingUser.setProfileImg(newUser.getProfileImg());

            // Save the updated user
            User updatedUser = userService.updateUser(id, existingUser); // Pass the user ID as well

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
