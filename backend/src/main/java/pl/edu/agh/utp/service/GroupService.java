package pl.edu.agh.utp.service;

import java.util.Collections;
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
import pl.edu.agh.utp.repository.GroupRepository;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@Data
public class GroupService {
  private final GroupRepository groupRepository;

  private final UserRepository userRepository;

  private final TransactionService transactionService;

  public Optional<GroupDTO> createGroup(GroupRequest request) {
    return userRepository
        .findById(request.userId())
        .map(
            user ->
                new Group(request.name(), Collections.singletonList(user), Collections.emptyList()))
        .map(groupRepository::save)
        .map(GroupDTO::fromGroup);
  }

  public List<SimpleTransactionDTO> getAllTransactionsByGroupId(Long groupId) {
    return groupRepository.findAllTransactionsByGroupId(groupId);
  }

  public Optional<TransactionDTO> addTransactionToGroup(
      Long groupId, TransactionRequest transaction) {
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
