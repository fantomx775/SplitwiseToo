package pl.edu.agh.utp.records;

import pl.edu.agh.utp.model.nodes.User;

public record Reimbursement(User debtor, User creditor, double amount) {}
