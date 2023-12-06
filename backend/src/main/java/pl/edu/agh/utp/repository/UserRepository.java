package pl.edu.agh.utp.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.edu.agh.utp.model.nodes.User;

public interface UserRepository extends Neo4jRepository<User, Long> {}
