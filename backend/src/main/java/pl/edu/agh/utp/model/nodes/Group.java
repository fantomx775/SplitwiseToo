package pl.edu.agh.utp.model.nodes;

import java.util.List;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Data
public class Group {
  @Id @GeneratedValue private Long id;
  private final String name;

  @Relationship(type = "CONTAINS_USER", direction = Relationship.Direction.OUTGOING)
  private final List<User> users;

  @Relationship(type = "CONTAINS_TRANSACTION", direction = Relationship.Direction.OUTGOING)
  private final List<Transaction> transactions;

  public void addUser(User user) {
    users.add(user);
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }
}
