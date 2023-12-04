package pl.edu.agh.utp.model.nodes;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@NoArgsConstructor
public class Group {

  public Group(String name) {
    this.name = name;
    this.users = new ArrayList<>();
    this.transactions = new ArrayList<>();
  }

  @Id @GeneratedValue private Long id;

  private String name;

  @Relationship(type = "CONTAINS_USER", direction = Relationship.Direction.OUTGOING)
  private List<User> users;

  @Relationship(type = "CONTAINS_TRANSACTION", direction = Relationship.Direction.OUTGOING)
  private List<Transaction> transactions;

  public void addUser(User user) {
    users.add(user);
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }
}
