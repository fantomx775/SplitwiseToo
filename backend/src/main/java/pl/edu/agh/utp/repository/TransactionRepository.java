package pl.edu.agh.utp.repository;

import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.utp.model.nodes.Transaction;

@Repository
public interface TransactionRepository extends Neo4jRepository<Transaction, UUID> {}
