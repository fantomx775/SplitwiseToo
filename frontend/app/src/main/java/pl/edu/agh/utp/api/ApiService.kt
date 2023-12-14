package pl.edu.agh.utp.api

import pl.edu.agh.utp.model.LoginRequest
import pl.edu.agh.utp.model.RegisterRequest
import pl.edu.agh.utp.model.SimpleTransaction
import pl.edu.agh.utp.model.Transaction
import pl.edu.agh.utp.model.TransactionRequest
import pl.edu.agh.utp.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("group/{id}/transactions")
    fun getTransactions(@Path("id") groupId: Long): Call<List<SimpleTransaction>>

    @GET("transaction/{id}")
    fun getTransaction(@Path("id") transactionId: Long): Call<Transaction>

    @POST("group/{id}/transactions")
    fun addTransaction(
        @Path("id") groupId: Long,
        @Body transaction: TransactionRequest
    ): Call<Transaction>

    @POST("/user/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<User>

    @POST("/user/create")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<User>
}