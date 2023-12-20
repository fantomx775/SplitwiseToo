package pl.edu.agh.utp.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.utp.dto.request.GroupRequest;
import pl.edu.agh.utp.dto.request.TransactionRequest;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.dto.response.SimpleTransactionDTO;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.dto.response.UserDTO;
import pl.edu.agh.utp.service.GroupService;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {

  private final GroupService groupService;

  @PostMapping("/create")
  public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupRequest request) {
    return ResponseEntity.ok(
        groupService
            .createGroup(request)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
  }

  @GetMapping("/{id}/users")
  public ResponseEntity<List<UserDTO>> getAllUsersByGroupId(@PathVariable("id") Long groupId) {
    return ResponseEntity.ok(groupService.getAllUsersByGroupId(groupId));
  }

  @PostMapping("/{id}/user")
  public ResponseEntity<GroupDTO> addUserToGroup(@PathVariable("id") Long groupId, @RequestBody List<String> emails) {
    return ResponseEntity.ok(
        groupService
            .addUsersToGroup(groupId, emails)
            .getOrElseThrow(
                (message) -> new ResponseStatusException(HttpStatus.NOT_FOUND, message))
            );
  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity<List<SimpleTransactionDTO>> getAllTransactionsByGroupId(
      @PathVariable("id") Long groupId) {
    return ResponseEntity.ok(groupService.getAllTransactionsByGroupId(groupId));
  }

  @PostMapping("/{id}/transactions")
  public ResponseEntity<TransactionDTO> addTransactionToGroup(
      @PathVariable("id") Long groupId, @RequestBody TransactionRequest transaction) {
    return ResponseEntity.ok(
        groupService
            .addTransactionToGroup(groupId, transaction)
            .getOrElseThrow(
                (message) -> new ResponseStatusException(HttpStatus.NOT_FOUND, message)));
  }
}
