package pl.edu.agh.utp.dto.request;



import java.util.List;


public record TransactionRequest(
    String description,
    String date,
    Long categoryId,
    double amount,
    Long paymentUserId,
    List<Long> debtsUserIds) {

}
