package pl.edu.agh.utp.model

import java.util.Date

data class SimpleTransaction(val transactionId: Long, val description: String, val date: Date) {
}