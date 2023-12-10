package pl.edu.agh.utp.service;

import java.util.Optional;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.repository.TransactionRepository;

@Service
@Data
public class TransactionService {

  private final TransactionRepository transactionRepository;

  public Optional<TransactionDTO> findTransactionById(Long id) {
    return transactionRepository.findById(id).map(TransactionDTO::fromTransaction);
  }
}
