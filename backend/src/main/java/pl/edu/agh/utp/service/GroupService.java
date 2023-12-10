package pl.edu.agh.utp.service;

import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.request.TransactionRequest;
import pl.edu.agh.utp.dto.response.SimpleTransactionDTO;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.repository.GroupRepository;

@Service
@Data
public class GroupService {
  private final GroupRepository groupRepository;

  private final TransactionService transactionService;

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
