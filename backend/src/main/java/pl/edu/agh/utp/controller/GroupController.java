package pl.edu.agh.utp.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.utp.dto.request.TransactionRequest;
import pl.edu.agh.utp.dto.response.SimpleTransactionDTO;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.service.GroupService;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {

  private final GroupService groupService;

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
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid group id")));
  }
}
