package pl.edu.agh.utp.records.dto;

import pl.edu.agh.utp.records.Reimbursement;

public record ReimbursmentDTO(UserDTO debtor, UserDTO creditor, double amount) {
  public static ReimbursmentDTO fromReimbursment(Reimbursement reimbursement) {
    return new ReimbursmentDTO(
        UserDTO.fromUser(reimbursement.debtor()),
        UserDTO.fromUser(reimbursement.creditor()),
        reimbursement.amount());
  }
}
