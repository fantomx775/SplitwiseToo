package pl.edu.agh.utp.records;

import pl.edu.agh.utp.model.nodes.User;

public record Reimbursment(User debtor, User creditor, double amount) {
}
