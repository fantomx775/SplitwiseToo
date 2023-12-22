package pl.edu.agh.utp.model

import java.util.UUID

data class Transaction(
    val transactionId: UUID,
    val description: String,
    val date: String,
    val category: Category,
    val payment: Payment,
    val debts: List<Debt>
)