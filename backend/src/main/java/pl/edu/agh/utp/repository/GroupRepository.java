package pl.edu.agh.utp.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.utp.model.nodes.Group;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.records.UserBalance;
import pl.edu.agh.utp.records.simple.SimpleTransaction;

public interface GroupRepository extends Neo4jRepository<Group, UUID> {
  @Query(
      "MATCH (g:Group)-[:CONTAINS_TRANSACTION]->(t:Transaction) WHERE g.id = $groupId RETURN t.id AS transactionId, t.description AS description, t.date AS date")
  List<SimpleTransaction> findAllTransactionsByGroupId(@Param("groupId") UUID groupId);

  // FIXME : This query is not working maybe?? Please check
  @Query(
      """
                  MATCH (g:Group)-[:CONTAINS_USER]->(u:User)
                  OPTIONAL MATCH (u)-[o:OWES]->(d:Transaction)
                  OPTIONAL MATCH (u)-[p:PAID]->(p:Transaction)
                  WHERE g.id = $groupId
                  WITH u, COALESCE(SUM(o.amount), 0) AS owesAmount, COALESCE(SUM(p.amount), 0) AS paidAmount
                  RETURN u as user, owesAmount - paidAmount AS balance
                  """)
  List<UserBalance> findAllBalancesByGroupId(@Param("groupId") UUID groupId);

  @Query(
      "MATCH (g:Group)-[:CONTAINS_USER]->(u:User) WHERE g.id = $groupId RETURN u.id AS id, u.name  AS name, u.email AS email, u.password AS password")
  List<User> findAllUsersByGroupId(@Param("groupId") UUID groupId);
}
