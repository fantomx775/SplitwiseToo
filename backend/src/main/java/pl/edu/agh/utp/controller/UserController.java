package pl.edu.agh.utp.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.utp.dto.request.LoginRequest;
import pl.edu.agh.utp.dto.request.RegisterRequest;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.dto.response.UserDTO;
import pl.edu.agh.utp.exceptions.InvalidPasswordException;
import pl.edu.agh.utp.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("{id}/groups")
  public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable("id") Long userId) {
    return ResponseEntity.ok(userService.findGroupsByUserId(userId));
  }

  @PostMapping("/create")
  public ResponseEntity<UserDTO> createUser(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(
        userService
            .createUser(request)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")));
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
    try {
      return ResponseEntity.ok(
          userService
              .authenticateUser(loginRequest)
              .orElseThrow(
                  () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    } catch (InvalidPasswordException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
    }
  }
}
