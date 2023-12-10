package pl.edu.agh.utp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.utp.dto.response.TransactionDTO;
import pl.edu.agh.utp.service.TransactionService;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @GetMapping("/{id}")
  public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        transactionService
            .findTransactionById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")));
  }
}
