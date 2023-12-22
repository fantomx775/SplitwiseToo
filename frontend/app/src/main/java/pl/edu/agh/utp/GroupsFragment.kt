package pl.edu.agh.utp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.utp.manager.UserManager
import java.util.UUID

class GroupsFragment : Fragment(), GroupAdapter.OnGroupClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupAdapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        val addGroupButton: Button = view.findViewById(R.id.add_group_button)
        addGroupButton.setOnClickListener {
            navigateToAddGroupFragment()
        }

        recyclerView = view.findViewById(R.id.groups_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        groupAdapter = GroupAdapter(this)
        recyclerView.adapter = groupAdapter
        return view
    }

    override fun onResume() {
        super.onResume()
        fetchUserGroups()
    }

    private fun fetchUserGroups() {
        val userManager = UserManager(requireContext())
        if (userManager.isLoggedIn()) {
            userManager.getUser()?.userId?.let { userId ->
                groupAdapter.fetchUserGroups(userId)
            } ?: Log.e("FetchUserGroups", "User ID is null")
        }
    }


    private fun navigateToAddGroupFragment() {
        val addGroupFragment = AddGroupFragment(groupAdapter) // TODO: remove passing adapter once dependency injection works
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, addGroupFragment)
        transaction.addToBackStack(null) // TODO: ability to return to previous view
        transaction.commit()
    }

    private fun navigateToTransactionsFragment(groupId: UUID) {
        val transactionFragment = TransactionsFragment(groupId)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, transactionFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onGroupClick(groupId: UUID) {
        navigateToTransactionsFragment(groupId)
    }
}