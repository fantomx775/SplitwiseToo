package pl.edu.agh.utp.model

data class TransactionRequest(
    val description: String,
    val date: String,
    val category: String,
    val amount: Double,
    val paymentUserId: Long,
    val debtsUserIds: List<Long>
) {
}
