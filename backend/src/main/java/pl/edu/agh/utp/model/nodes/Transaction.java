package pl.edu.agh.utp.model.nodes;

import java.util.List;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import pl.edu.agh.utp.model.relationships.Debt;
import pl.edu.agh.utp.model.relationships.Payment;

@Node
@Data
public class Transaction {
  @Id @GeneratedValue private Long id;
  private final String date;
  private final String description;

  @Relationship(type = "IS_OF_CATEGORY", direction = Relationship.Direction.OUTGOING)
  private final Category category;

  @Relationship(type = "MADE_PAYMENT", direction = Relationship.Direction.OUTGOING)
  private final Payment payment;

  @Relationship(type = "OWES", direction = Relationship.Direction.OUTGOING)
  private final List<Debt> debts;
}
