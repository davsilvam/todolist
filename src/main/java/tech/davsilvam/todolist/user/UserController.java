package tech.davsilvam.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity<Object> register(@RequestBody UserModel user) {
    var userExists = this.userRepository.findByUsername(user.getUsername());

    if (userExists != null) {
      System.out.println("User already exists!");

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
    }

    var hashedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

    user.setPassword(hashedPassword);

    var createdUser = this.userRepository.save(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

}
