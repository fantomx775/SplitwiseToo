package pl.edu.agh.utp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.utp.service.TransactionService;

@RestController
@RequestMapping(path = "transaction")
@AllArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;
}
