package pl.edu.agh.utp


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import pl.edu.agh.utp.api.ApiObject
import pl.edu.agh.utp.model.SimpleTransaction

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID


class TransactionsAdapter(private val groupId: UUID, private val clickListener: TransactionClickListener) : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {


    private var transactions: MutableList<SimpleTransaction> =  mutableListOf()

    private val apiService = ApiObject.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false))

    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.itemView.findViewById<TextView>(R.id.textViewDescription).text = transaction.description
        holder.itemView.findViewById<TextView>(R.id.textViewDate).text = transaction.date

        holder.itemView.setOnClickListener {
            clickListener.onTransactionClick(transaction.transactionId)
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }


    fun fetchTransactions() {
        apiService.getTransactions(groupId).enqueue(object : Callback<List<SimpleTransaction>> {
            override fun onResponse(
                call: Call<List<SimpleTransaction>>,
                response: Response<List<SimpleTransaction>>
            ) {
                if (response.isSuccessful) {
                    Log.i("Transactions", "Success: ${response.body()}")
                    val newTransactions = response.body() ?: emptyList()
                    transactions.clear()
                    transactions.addAll(newTransactions)
                    notifyDataSetChanged()

                } else {
                    Log.e("Transactions", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SimpleTransaction>>, t: Throwable) {
                Log.e("Transactions", "Error: ${t.message}")
            }
        })
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface TransactionClickListener {
        fun onTransactionClick(transactionId: UUID)
    }
}



