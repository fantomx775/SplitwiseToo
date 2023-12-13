package pl.edu.agh.utp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.request.GroupRequest;
import pl.edu.agh.utp.dto.request.TransactionRequest;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.dto.response.SimpleTransactionDTO;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.repository.GroupRepository;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@Data
public class GroupService {
  private final GroupRepository groupRepository;

  private final UserRepository userRepository;

  private final TransactionService transactionService;

  public GroupDTO createGroup(GroupRequest request) {
      Optional<User> userOpt = userRepository.findById(request.userId());
      ArrayList<User> users = new ArrayList<>();
      users.add(userOpt.get()); // TODO idk if it's done properly
      ArrayList<Transaction> transactions = new ArrayList<>();
      Group group = new Group(request.name(), users, transactions);
      return GroupDTO.fromGroup(groupRepository.save(group));
  }

  public Optional<Group> getGroupById(Long id) {
    return groupRepository.findById(id);
  }

  public List<SimpleTransactionDTO> getAllTransactionsByGroupId(Long groupId) {
    return groupRepository.findAllTransactionsByGroupId(groupId);
  }

  public Optional<TransactionDTO> addTransactionToGroup(Long groupId, TransactionRequest transaction) {
    return groupRepository
        .findById(groupId)
        .map(
            group -> {
              var transactionToSave = transactionService.createTransactionFromRequest(transaction);
              group.getTransactions().add(transactionToSave);
              groupRepository.save(group);
              return TransactionDTO.fromTransaction(transactionToSave);
            });
  }
}
