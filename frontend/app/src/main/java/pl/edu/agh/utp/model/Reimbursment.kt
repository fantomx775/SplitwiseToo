package pl.edu.agh.utp.model

import pl.edu.agh.utp.model.user.User

data class Reimbursment(
    val debtor:User,
    val creditor:User,
    val amount:Double
)
