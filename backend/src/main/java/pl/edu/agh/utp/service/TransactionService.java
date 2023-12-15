package pl.edu.agh.utp.service;

import io.vavr.control.Either;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.request.TransactionRequest;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.model.nodes.Category;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.model.nodes.User;
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

  public Either<String, Transaction> createTransactionFromRequest(
      TransactionRequest transactionRequest) {
    Optional<Category> category = categoryRepository.findByName(transactionRequest.category());
    if (category.isEmpty()) {
      return Either.left("Category not found");
    }
    Optional<User> paymentUser = userRepository.findById(transactionRequest.paymentUserId());
    if (paymentUser.isEmpty()) {
      return Either.left("Payment user not found");
    }

    List<User> debtsUsers = userRepository.findAllById(transactionRequest.debtsUserIds());
    if (debtsUsers.size() != transactionRequest.debtsUserIds().size()) {
      return Either.left("Debts users not found");
    }

    Payment payment = new Payment(paymentUser.get(), transactionRequest.amount());

    double amountToPay = getAmountToPay(transactionRequest);

    List<Debt> debts = debtsUsers.stream().map(user -> new Debt(user, amountToPay)).toList();

    Transaction transaction =
        new Transaction(
            transactionRequest.description(),
            transactionRequest.date(),
            category.get(),
            payment,
            debts);
    return Either.right(transactionRepository.save(transaction));
  }

  private double getAmountToPay(TransactionRequest transactionRequest) {
    var amount = transactionRequest.amount() / transactionRequest.debtsUserIds().size();

    return round(amount, 2);
  }

  private double round(double value, int places) {
    return new BigDecimal(value).setScale(places, RoundingMode.DOWN).doubleValue();
  }
}
