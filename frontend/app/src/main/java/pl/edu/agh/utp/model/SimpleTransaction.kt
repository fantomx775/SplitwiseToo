package pl.edu.agh.utp.model

import java.util.UUID

data class SimpleTransaction(val transactionId: UUID, val description: String, val date: String)