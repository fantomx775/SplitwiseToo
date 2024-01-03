package pl.edu.agh.utp.records.dto;

import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.records.Reimbursment;

public record ReimbursmentDTO(UserDTO debtor, UserDTO creditor, double amount) {
    public static ReimbursmentDTO fromReimbursment(Reimbursment reimbursment) {
        return new ReimbursmentDTO(
                UserDTO.fromUser(reimbursment.debtor()),
                UserDTO.fromUser(reimbursment.creditor()),
                reimbursment.amount());
    }
}
