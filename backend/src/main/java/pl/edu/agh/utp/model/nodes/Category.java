package pl.edu.agh.utp.model.nodes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@NoArgsConstructor
@Getter
@Node
public class Category {

  @Id @GeneratedValue private Long id;

  public Category(String name, String description) {
    this.name = name;
    this.description = description;
  }

  private String name;
  private String description;
}
