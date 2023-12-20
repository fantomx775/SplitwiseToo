package pl.edu.agh.utp.service;

import io.vavr.control.Either;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.records.request.GroupRequest;
import pl.edu.agh.utp.records.request.TransactionRequest;
import pl.edu.agh.utp.records.simple.SimpleGroup;
import pl.edu.agh.utp.records.simple.SimpleTransaction;
import pl.edu.agh.utp.repository.GroupRepository;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@AllArgsConstructor
public class GroupService {
  private final GroupRepository groupRepository;

  private final UserRepository userRepository;

  private final TransactionService transactionService;

  @Transactional
  public Optional<SimpleGroup> createGroup(GroupRequest request) {
    return userRepository
        .findById(request.userId())
        .map(
            user ->
                new Group(request.name(), Collections.singletonList(user), Collections.emptyList()))
        .map(groupRepository::save)
        .map(SimpleGroup::fromGroup);
  }

  public List<SimpleTransaction> getAllTransactionsByGroupId(UUID groupId) {
    return groupRepository.findAllTransactionsByGroupId(groupId);
  }

  public List<User> getAllUsersByGroupId(UUID groupId) {
    return groupRepository.findAllUsersByGroupId(groupId);
  }

  @Transactional
  public Either<String, Transaction> addTransactionToGroup(
      UUID groupId, TransactionRequest transactionRequest) {

    return groupRepository
        .findById(groupId)
        .map(group -> processGroupWithTransaction(group, transactionRequest))
        .orElse(Either.left("Invalid group userId"));
  }

  private Either<String, Transaction> processGroupWithTransaction(
      Group group, TransactionRequest transactionRequest) {

    var transactionEither = transactionService.createTransactionFromRequest(transactionRequest);
    return transactionEither
        .map(
            transaction -> {
              group.getTransactions().add(transaction);
              groupRepository.save(group);
              return transaction;
            })
        .orElse(() -> Either.left(transactionEither.getLeft()));
  }

  @Transactional
  public Optional<Group> addUsersToGroup(UUID groupId, List<String> emails) {
    return groupRepository.findById(groupId).map(group -> updateGroupWithUsers(group, emails));
  }

  private Group updateGroupWithUsers(Group group, List<String> emails) {
    List<User> usersToAdd =
        emails.stream().map(userRepository::findByEmail).flatMap(Optional::stream).toList();
    group.getUsers().addAll(usersToAdd);
    return groupRepository.save(group);
  }
}
