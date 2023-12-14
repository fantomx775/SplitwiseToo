package pl.edu.agh.utp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TransactionsFragment( private val groupId:Long) : Fragment(),
    TransactionsAdapter.TransactionClickListener {

    private var param1: String? = null
    private var param2: String? = null

    override fun onTransactionClick(transactionId: Long) {
        navigateToTransactionDetailsFragment(transactionId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionsAdapter: TransactionsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view = inflater.inflate(R.layout.fragment_transactions, container, false)

        val btnAdd: Button = view.findViewById(R.id.add_transaction_button)

        btnAdd.setOnClickListener {
            navigateToAddTransactionFragment()
        }

        recyclerView = view.findViewById(R.id.transactions_recycler_view)
        transactionsAdapter = TransactionsAdapter(groupId, this)
        recyclerView.adapter = transactionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        transactionsAdapter.fetchTransactions()

        return view
    }

    override fun onResume() {
        super.onResume()
        transactionsAdapter.fetchTransactions()
    }

    private fun navigateToAddTransactionFragment() {
        val fragment = AddTransactionFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun navigateToTransactionDetailsFragment(transactionId: Long) {
        val fragment = TransactionDetailsFragment(transactionId)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }




}