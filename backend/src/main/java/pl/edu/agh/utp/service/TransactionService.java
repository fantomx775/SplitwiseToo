package pl.edu.agh.utp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.repository.TransactionRepository;

@Service
@AllArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;

  public void createTransaction(Transaction transaction) {
    transactionRepository.save(transaction);
  }

  public void deleteTransaction(Transaction transaction) {
    transactionRepository.delete(transaction);
  }
}
