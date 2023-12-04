package pl.edu.agh.utp.model.nodes;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import pl.edu.agh.utp.model.relationships.Debt;
import pl.edu.agh.utp.model.relationships.Payment;

@Node
@NoArgsConstructor
@Getter
public class Transaction {
  @Id @GeneratedValue private Long id;

  public Transaction(
      String date, String description, Category category, List<Debt> debts, Payment payment) {
    this.date = date;
    this.description = description;
    this.category = category;
    this.debts = debts;
    this.payment = payment;
  }

  @Relationship(type = "MADE_PAYMENT", direction = Relationship.Direction.OUTGOING)
  private Payment payment;

  @Relationship(type = "OWES", direction = Relationship.Direction.OUTGOING)
  private List<Debt> debts;

  @Relationship(type = "IS_OF_CATEGORY", direction = Relationship.Direction.OUTGOING)
  private Category category;

  private String date;

  private String description;
}
