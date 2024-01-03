package pl.edu.agh.utp.service;

import io.vavr.control.Either;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.utp.model.nodes.Category;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.records.Reimbursement;
import pl.edu.agh.utp.records.UserBalance;
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
    List<User> usersToAdd = userRepository.findAllByEmail(emails);
    group.getUsers().addAll(usersToAdd);
    return groupRepository.save(group);
  }

  @Transactional
  public List<UserBalance> getAllBalancesByGroupId(UUID groupId) {
    return groupRepository.findAllBalancesByGroupId(groupId);
  }

  @Transactional
  public List<Reimbursement> getReimbursementsByGroupId(UUID groupId) {
    List<UserBalance> balances = getAllBalancesByGroupId(groupId);
    return calculateReimbursements(balances);
  }

  @Transactional
  public List<UserBalance> getBalancesByGroupIdAndCategory(UUID groupId, List<Category> categories) {
    return groupRepository.findBalancesByGroupIdAndCategory(groupId,categories);
  }

  @Transactional
  public List<Reimbursement> getReimbursementsByGroupIdAndCategory(UUID groupId,List<Category> categories) {
    List<UserBalance> balances = getBalancesByGroupIdAndCategory(groupId,categories);
    return calculateReimbursements(balances);
  }

  public static List<Reimbursement> calculateReimbursements(List<UserBalance> balances) {
    // split for two lists with negative and positive balances
    List<UserBalance> negativeBalances =
        balances.stream()
            .filter(balance -> balance.balance() <= 0)
            .sorted(Comparator.comparing(UserBalance::balance))
            .toList();
    List<UserBalance> positiveBalances =
        balances.stream()
            .filter(balance -> balance.balance() > 0)
            .sorted(Comparator.comparing(UserBalance::balance))
            .collect(Collectors.toList());
    List<Reimbursement> reimbursements = new ArrayList<>();

    for (UserBalance negativeBalance : negativeBalances) {
      double currentNegativeBalanceValue = negativeBalance.balance();
      while (currentNegativeBalanceValue < 0) {
        UserBalance positiveBalance = positiveBalances.get(0);
        double positiveBalanceValue = positiveBalance.balance();
        double debtValue = positiveBalanceValue + currentNegativeBalanceValue;
        if (debtValue <= 0) {
          reimbursements.add(
              new Reimbursement(
                  positiveBalance.user(), negativeBalance.user(), positiveBalanceValue));
          positiveBalances.remove(0);
          currentNegativeBalanceValue = debtValue;
        } else {
          reimbursements.add(
              new Reimbursement(
                  positiveBalance.user(), negativeBalance.user(), -currentNegativeBalanceValue));
          positiveBalances.set(0, new UserBalance(positiveBalance.user(), debtValue));
          currentNegativeBalanceValue = 0;
        }
      }
    }
    return reimbursements;
  }

  // FIXME: make this better
  public Optional<Group> findGroupById(UUID groupId) {
    return groupRepository.findById(groupId);
  }
}
