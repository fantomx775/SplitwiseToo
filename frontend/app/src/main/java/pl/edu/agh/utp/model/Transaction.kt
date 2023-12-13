package pl.edu.agh.utp.model

import pl.edu.agh.utp.model.Category
import pl.edu.agh.utp.model.Debt
import pl.edu.agh.utp.model.Payment

data class Transaction(
//    Long id,
//    String description,
//    String date,
//    Category category,
//    PaymentDTO payment,
//    List<PaymentDTO> debts)
    val id: Long,
    val description: String,
    val date: String,
    val category: Category,
    val payment: Payment,
    val debts: List<Debt>
) {
}