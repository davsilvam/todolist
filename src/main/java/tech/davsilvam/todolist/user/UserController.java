package tech.davsilvam.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping
  public ResponseEntity<User> register(@RequestBody UserRequestDTO data) throws Exception {
    User newUser = new User(data);

    User userExists = userRepository.findByUsername(newUser.getUsername());

    if (userExists != null) {
      throw new Exception("User already exists!");
    }

    String hashedPassword = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());
    newUser.setPassword(hashedPassword);

    User createdUser = userRepository.save(newUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

}
