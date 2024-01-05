package pl.edu.agh.utp.controller;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.utp.model.nodes.Category;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.records.dto.ReimbursmentDTO;
import pl.edu.agh.utp.records.dto.TransactionDTO;
import pl.edu.agh.utp.records.dto.UserDTO;
import pl.edu.agh.utp.records.request.GroupRequest;
import pl.edu.agh.utp.records.request.TransactionRequest;
import pl.edu.agh.utp.records.simple.SimpleGroup;
import pl.edu.agh.utp.records.simple.SimpleTransaction;
import pl.edu.agh.utp.service.GroupService;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

  private final GroupService groupService;

  @PostMapping
  public SimpleGroup createGroup(@RequestBody GroupRequest request) {
    return groupService
        .createGroup(request)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  @GetMapping("/{id}/users")
  public List<UserDTO> getAllUsersByGroupId(@PathVariable("id") UUID groupId) {
    return groupService.getAllUsersByGroupId(groupId).stream().map(UserDTO::fromUser).toList();
  }

  @PostMapping("/{id}/users")
  public SimpleGroup addUsersToGroup(
      @PathVariable("id") UUID groupId, @RequestBody List<String> emails) {
    return groupService
        .addUsersToGroup(groupId, emails)
        .map(SimpleGroup::fromGroup)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
  }

  @GetMapping("/{id}/transactions")
  public List<SimpleTransaction> getAllTransactionsByGroupId(@PathVariable("id") UUID groupId) {
    return groupService.getAllTransactionsByGroupId(groupId);
  }

  // FIXME: WTF is this?
  @PostMapping("/{id}/transactions/categories")
  public List<SimpleTransaction> getTransactionsByGroupIdAndCategories(
      @PathVariable("id") UUID groupId, @RequestBody List<Category> categories) {
    var transactions =
        groupService
            .findGroupById(groupId)
            .map(Group::getTransactions)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

    return transactions.stream()
        .filter(transaction -> categories.contains(transaction.getCategory()))
        .map(
            transaction ->
                new SimpleTransaction(
                    transaction.getId(), transaction.getDescription(), transaction.getDate()))
        .toList();
  }

  @PostMapping("/{id}/transactions")
  public TransactionDTO addTransactionToGroup(
      @PathVariable("id") UUID groupId, @RequestBody TransactionRequest transaction) {
    return groupService
        .addTransactionToGroup(groupId, transaction)
        .map(TransactionDTO::fromTransaction)
        .getOrElseThrow((message) -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
  }

  @GetMapping("/{id}/reimbursements")
  public List<ReimbursmentDTO> getReimbursementsByGroupId(@PathVariable("id") UUID groupId) {
    return groupService.getReimbursementsByGroupId(groupId).stream()
        .map(ReimbursmentDTO::fromReimbursment)
        .toList();
  }

  @PostMapping("/{id}/reimbursements/categories")
  public List<ReimbursmentDTO> getReimbursementsByGroupIdAndCategories(
      @PathVariable("id") UUID groupId, @RequestBody List<Category> categories) {

    return groupService.getReimbursementsByGroupIdAndCategory(groupId,categories).stream()
            .map(ReimbursmentDTO::fromReimbursment)
            .toList();
  }

  @GetMapping("/{id}/categories")
  public List<Category> getCategoriesByGroup(
      @PathVariable("id") UUID groupId) {
    return groupService.getCategoriesByGroupId(groupId);
  }
}
