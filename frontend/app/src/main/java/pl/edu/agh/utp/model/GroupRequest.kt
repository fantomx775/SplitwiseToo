package pl.edu.agh.utp.model

import java.util.UUID

data class GroupRequest(
    val name: String,
    val userId: UUID
)
