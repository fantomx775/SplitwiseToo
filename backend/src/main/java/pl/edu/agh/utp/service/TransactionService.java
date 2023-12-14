package pl.edu.agh.utp.service;

import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.request.TransactionRequest;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.model.nodes.Category;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.model.relationships.Debt;
import pl.edu.agh.utp.model.relationships.Payment;
import pl.edu.agh.utp.repository.CategoryRepository;
import pl.edu.agh.utp.repository.TransactionRepository;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@Data
public class TransactionService {

  private final TransactionRepository transactionRepository;

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  public Optional<TransactionDTO> findTransactionById(Long id) {
    return transactionRepository.findById(id).map(TransactionDTO::fromTransaction);
  }

  public Transaction createTransactionFromRequest(TransactionRequest transactionRequest) {
    Category category = categoryRepository.findById(transactionRequest.categoryId()).orElseThrow();
    Payment payment =
        new Payment(
            userRepository.findById(transactionRequest.paymentUserId()).orElseThrow(),
            transactionRequest.amount());
    double amountToPay = transactionRequest.amount() / transactionRequest.debtsUserIds().size();

    List<Debt> debts =
        transactionRequest.debtsUserIds().stream()
            .map(userId -> new Debt(userRepository.findById(userId).orElseThrow(), amountToPay))
            .toList();

    Transaction transaction =
        new Transaction(
            transactionRequest.description(), transactionRequest.date(), category, payment, debts);
    return transactionRepository.save(transaction);
  }
}
