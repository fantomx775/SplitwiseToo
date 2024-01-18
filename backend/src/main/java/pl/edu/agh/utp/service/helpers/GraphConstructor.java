package pl.edu.agh.utp.service.helpers;

import pl.edu.agh.utp.records.TransactionsGraph;
import pl.edu.agh.utp.records.dto.PaymentDTO;
import pl.edu.agh.utp.records.dto.TransactionDTO;
import pl.edu.agh.utp.records.dto.UserDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphConstructor {

    private GraphConstructor() {}

    public static TransactionsGraph constructGraph(List<UserDTO> users, List<TransactionDTO> transactions) {
        List<TransactionsGraph.Vertex> vertices = new LinkedList<>();
        List<TransactionsGraph.Edge> edges = new LinkedList<>();

        for (UserDTO user : users) {
            vertices.add(new TransactionsGraph.Vertex(user.userId(), user.name()));
        }

        for (TransactionDTO transaction : transactions) {
            PaymentDTO payment = transaction.payment();
            for (PaymentDTO debt : transaction.debts()) {
                edges.add(new TransactionsGraph.Edge(debt.user().userId(), payment.user().userId(), debt.amount()));
            }
        }

        return new TransactionsGraph(vertices, edges);
    }

    public static TransactionsGraph constructGraphMerged(List<UserDTO> users, List<TransactionDTO> transactions) {
        List<TransactionsGraph.Vertex> vertices = new LinkedList<>();
        List<TransactionsGraph.Edge> edges = new LinkedList<>();
        HashMap<TransactionsGraph.Pair, Double> debts = new HashMap<>();

        for (UserDTO user : users) {
            vertices.add(new TransactionsGraph.Vertex(user.userId(), user.name()));
        }

        for (TransactionDTO transaction : transactions) {
            PaymentDTO payment = transaction.payment();
            for (PaymentDTO debt : transaction.debts()) {
                TransactionsGraph.Pair pair = new TransactionsGraph.Pair(debt.user().userId(), payment.user().userId());
                debts.putIfAbsent(pair, 0d);
                debts.put(pair, debts.get(pair) + debt.amount());

                TransactionsGraph.Pair reversedPair = new TransactionsGraph.Pair(payment.user().userId(), debt.user().userId());
                debts.putIfAbsent(reversedPair, 0d);
                debts.put(pair, debts.get(pair) - debt.amount());
            }
        }

        for (Map.Entry<TransactionsGraph.Pair, Double> debt : debts.entrySet()) {
            if (debt.getValue() > 0) {
                edges.add(new TransactionsGraph.Edge(debt.getKey().from(), debt.getKey().to(), debt.getValue()));
            }
        }

        return new TransactionsGraph(vertices, edges);
    }

}
