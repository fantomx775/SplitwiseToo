package pl.edu.agh.utp.model

data class Transaction(

    val transactionId: Long,
    val description: String,
    val date: String,
    val category: Category,
    val payment: Payment,
    val debts: List<Debt>
) {
}