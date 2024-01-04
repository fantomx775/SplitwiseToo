package pl.edu.agh.utp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import pl.edu.agh.utp.R
import pl.edu.agh.utp.viewmodel.SummaryRecyclerViewAdapter
import pl.edu.agh.utp.viewmodel.TransactionsAdapter
import java.util.UUID


import pl.edu.agh.utp.view.transaction.AddTransactionFragment
import pl.edu.agh.utp.view.transaction.TransactionDetailsFragment




class SummaryFragment (private val groupId: UUID): Fragment() {

    private var columnCount = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var reimbursementAdapter: SummaryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view = inflater.inflate(R.layout.fragment_summary, container, false)


        recyclerView = view.findViewById(R.id.summary_recycler_view)
        reimbursementAdapter = SummaryRecyclerViewAdapter(groupId)
        recyclerView.adapter = reimbursementAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return view
    }

    override fun onResume() {
        super.onResume()
        reimbursementAdapter.fetchReimburesments()
    }
}