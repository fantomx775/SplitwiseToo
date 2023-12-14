package pl.edu.agh.utp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.utp.dto.request.SignUpRequest;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.dto.response.UserDTO;
import pl.edu.agh.utp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/groups")
    public ResponseEntity<List<GroupDTO>> getUserGroups(
            @RequestBody Long userId) {
        return ResponseEntity.ok(userService.findGroupsByUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(  // TODO error handling
                                                @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
}