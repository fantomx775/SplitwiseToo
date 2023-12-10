package pl.edu.agh.utp.dto.response;

import java.util.List;
import pl.edu.agh.utp.model.relationships.Debt;
import pl.edu.agh.utp.model.relationships.Payment;

public record PaymentDTO(UserDTO user, double amount) {

  public static PaymentDTO fromPayment(Payment payment) {
    return new PaymentDTO(UserDTO.fromUser(payment.getUser()), payment.getAmount());
  }

  public static List<PaymentDTO> fromDebt(List<Debt> debts) {
    return debts.stream()
        .map(debt -> new PaymentDTO(UserDTO.fromUser(debt.getUser()), debt.getAmount()))
        .toList();
  }
}
