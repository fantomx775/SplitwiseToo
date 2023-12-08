package pl.edu.agh.utp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.utp.repository.GroupRepository;
import pl.edu.agh.utp.repository.UserRepository;

@Configuration
public class Configurator {

  @Bean
  CommandLineRunner commandLineRunner(GroupRepository groupRepository, UserRepository repository) {
    return args -> {
      System.out.println(repository.findGroupsByUserId(0L));
      //      User kowalski = new User("Kowalski",  "k@agh.pl", "12332");
      //      User michas = new User("Michaś",  "m@agh.pl", "12332");
      //
      //      Debt kowalskiDebt = new Debt(kowalski, 100);
      //      Payment michasPayment = new Payment(michas, 100);
      //      Category alkoholowe = new Category("alkoholowe");
      //
      //      Transaction t =
      //          new Transaction(
      //              "2012-12-12", "transaction", alkoholowe, michasPayment,
      // List.of(kowalskiDebt));
      //
      //      Group group1 = new Group("group1", List.of(kowalski, michas), List.of(t));
      //      groupRepository.save(group1);
    };
  }
}
