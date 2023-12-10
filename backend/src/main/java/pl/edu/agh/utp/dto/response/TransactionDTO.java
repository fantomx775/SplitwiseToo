package pl.edu.agh.utp.dto.response;

import java.util.List;
import pl.edu.agh.utp.model.nodes.Category;
import pl.edu.agh.utp.model.nodes.Transaction;

public record TransactionDTO(
    Long id,
    String description,
    String date,
    Category category,
    PaymentDTO payment,
    List<PaymentDTO> debts) {

  public static TransactionDTO fromTransaction(Transaction transaction) {
    return new TransactionDTO(
        transaction.getId(),
        transaction.getDescription(),
        transaction.getDate(),
        transaction.getCategory(),
        PaymentDTO.fromPayment(transaction.getPayment()),
        PaymentDTO.fromDebt(transaction.getDebts()));
  }
}
