package pl.edu.agh.utp.model

data class TransactionRequest(
    val description: String,
    val date: String,
    val categoryId: Long,
    val amount: Double,
    val paymentUserId: Long,
    val debtsUserIds: List<Long>
) {
}
