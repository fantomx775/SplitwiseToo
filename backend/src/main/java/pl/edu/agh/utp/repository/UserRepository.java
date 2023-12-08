package pl.edu.agh.utp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.utp.dto.response.GroupDTO;
import pl.edu.agh.utp.model.nodes.User;

public interface UserRepository extends Neo4jRepository<User, Long> {
  Optional<User> findByEmail(String email);

  @Query(
      "MATCH (g:Group)-[:CONTAINS_USER]->(u:User) WHERE ID(u) = $userId RETURN ID(g) AS groupId, g.name AS name")
  List<GroupDTO> findGroupsByUserId(@Param("userId") Long userId);
}
