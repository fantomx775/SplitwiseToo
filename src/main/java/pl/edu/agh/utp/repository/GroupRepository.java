package pl.edu.agh.utp.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.edu.agh.utp.model.nodes.Group;

public interface GroupRepository extends Neo4jRepository<Group, Long> {}
