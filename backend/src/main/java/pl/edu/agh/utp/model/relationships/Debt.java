package pl.edu.agh.utp.model.relationships;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import pl.edu.agh.utp.model.nodes.User;

@NoArgsConstructor
@Getter
@RelationshipProperties
public class Debt {

  public Debt(User user, double amount) {
    this.user = user;
    this.amount = amount;
  }

  @Id @GeneratedValue private Long id;

  @TargetNode private User user;

  private double amount;
}
