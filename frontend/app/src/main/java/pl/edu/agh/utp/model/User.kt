package pl.edu.agh.utp.model

import java.util.UUID

data class User(
    val userId: UUID,
    val name: String,
    val email: String
)
