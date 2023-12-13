package pl.edu.agh.utp.model

import pl.edu.agh.utp.User

data class Payment(

    val user: User,
    val amount: Double

) {
}

