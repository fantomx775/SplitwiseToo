package pl.edu.agh.utp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.utp.manager.UserManager


class AddGroupFragment(private val groupAdapter: GroupAdapter) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_group, container, false)

        val createButton: Button = view.findViewById(R.id.add_group_button)
        createButton.setOnClickListener {
            createGroup()
        }

        return view
    }

    private fun createGroup() {
        val groupName: String = view?.findViewById<View>(R.id.name_input).toString()
        val userId: Long = UserManager(requireContext()).getUser()?.userId!!
        groupAdapter.createGroup(groupName, userId)
    }
}