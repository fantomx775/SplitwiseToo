package pl.edu.agh.utp.repository;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.utp.dto.response.SimpleTransactionDTO;
import pl.edu.agh.utp.model.nodes.Group;

public interface GroupRepository extends Neo4jRepository<Group, Long> {
  @Query(
      "MATCH (g:Group)-[:CONTAINS_TRANSACTION]->(t:Transaction) WHERE ID(g) = $groupId RETURN ID(t) AS id, t.description AS description, t.date AS date")
  List<SimpleTransactionDTO> findAllTransactionsByGroupId(@Param("groupId") Long groupId);
}
