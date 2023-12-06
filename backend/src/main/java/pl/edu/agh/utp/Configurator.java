package pl.edu.agh.utp;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.utp.model.nodes.Category;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.model.nodes.Transaction;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.model.relationships.Debt;
import pl.edu.agh.utp.model.relationships.Payment;
import pl.edu.agh.utp.repository.CategoryRepository;
import pl.edu.agh.utp.repository.GroupRepository;
import pl.edu.agh.utp.repository.TransactionRepository;
import pl.edu.agh.utp.repository.UserRepository;

@Configuration
public class Configurator {

  @Bean
  CommandLineRunner commandLineRunner(
      UserRepository userRepository,
      GroupRepository groupRepository,
      CategoryRepository categoryRepository,
      TransactionRepository transactionRepository) {
    return args -> {

      //            userRepository.deleteAll();

      User kowalski = new User("Kowalski", "12332", "k@agh.pl");
      userRepository.save(kowalski);
      User michas = new User("Michaś", "12332", "m@agh.pl");
      userRepository.save(michas);
      Group group1 = new Group("group1");

      group1.addUser(kowalski);
      group1.addUser(michas);

      groupRepository.save(group1);

      Category alkoholowe = new Category("alkoholowe", "alkoholowe");

      categoryRepository.save(alkoholowe);

      Debt kowalskiDebt = new Debt(kowalski, 100);

      Payment michasPayment = new Payment(michas, 100);

      Transaction t =
          new Transaction(
              "2012-12-12", "transaction", alkoholowe, List.of(kowalskiDebt), michasPayment);
      transactionRepository.save(t);

      group1.addTransaction(t);
      groupRepository.save(group1);

      System.out.println(kowalski.getId());
      System.out.println(michas.getId());
      System.out.println("skad ten null");
      userRepository.save(kowalski);
      userRepository.save(michas);
    };
  }
}
