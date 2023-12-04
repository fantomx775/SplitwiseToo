package pl.edu.agh.utp.model.nodes;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
public class User {
  @Id @GeneratedValue private Long id;

  public User() {}

  public User(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  private String name;

  private String password;
  private String email;
}
