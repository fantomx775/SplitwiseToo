package pl.edu.agh.utp.model

import pl.edu.agh.utp.User

data class Debt(
    val user: User,
    val amount: Double

)
