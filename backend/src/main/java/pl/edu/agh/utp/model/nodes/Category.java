package pl.edu.agh.utp.model.nodes;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Category {
  @Id @GeneratedValue private Long id;
  private final String name;
}
