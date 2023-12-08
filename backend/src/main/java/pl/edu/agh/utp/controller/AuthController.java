package pl.edu.agh.utp.controller;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.utp.dto.request.LoginRequest;
import pl.edu.agh.utp.exceptions.InvalidPasswordException;
import pl.edu.agh.utp.exceptions.UserNotFoundException;
import pl.edu.agh.utp.service.UserService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
    try {
      var user = userService.authenticateUser(loginRequest);
      var groups = userService.findGroupsByUserId(user.id());
      return ResponseEntity.ok(Map.of("user", user, "groups", groups));
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    } catch (InvalidPasswordException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
    }
  }
}
