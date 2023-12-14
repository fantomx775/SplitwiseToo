package pl.edu.agh.utp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import pl.edu.agh.utp.api.ApiObject
import pl.edu.agh.utp.model.Group
import pl.edu.agh.utp.model.GroupRequest
import pl.edu.agh.utp.model.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupAdapter(private val clickListener: OnGroupClickListener) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    private var groupList: MutableList<Group> = mutableListOf()

    private val apiService = ApiObject.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groupList[position]

        holder.itemView.setOnClickListener {
            clickListener.onGroupClick(group.id)
        }

        holder.group_id.text = group.id.toString()
        holder.group_name.text = group.name
    }

    override fun getItemCount(): Int = groupList.size

    fun setItems(newGroups: MutableList<Group>) {
        groupList = newGroups
        notifyDataSetChanged()
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_id: TextView = itemView.findViewById(R.id.group_id)
        val group_name: TextView = itemView.findViewById(R.id.group_name)
    }

    interface OnGroupClickListener {
        fun onGroupClick(groupId: Long)
    }

    fun fetchUserGroups(userId: Long) {
        apiService.getUserGroups(userId).enqueue(object : Callback<List<Group>> {
            override fun onResponse(
                call: Call<List<Group>>,
                response: Response<List<Group>>
            ) {
                if (response.isSuccessful) {
                    Log.i("FetchUserGroups", "Success: ${response.body()}")
                    val userGroups = response.body() ?: emptyList()
                    groupList.clear()
                    groupList.addAll(userGroups)
                    notifyDataSetChanged()
                } else {
                    Log.e("FetchUserGroups", "Error: ${response.code()}")
                }
            }

            override fun onFailure(
                call: Call<List<Group>>,
                t: Throwable
            ) {
                Log.e("FetchUserGroups", "Error: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    fun createGroup(groupName: String, userId: Long) {
        val groupRequest = GroupRequest(groupName, userId)

        apiService.createGroup(groupRequest).enqueue(object : Callback<Group> {
            override fun onResponse(
                call: Call<Group>,
                response: Response<Group>
            ) {
                if (response.isSuccessful) {
                    Log.i("CreateGroup", "Success: ${response.body()}")
                    val createdGroup = response.body()
                    if (createdGroup != null) {
                        groupList.add(createdGroup)
                    }
                    notifyDataSetChanged()
                } else {
                    Log.e("CreateGroup", "Error: ${response.code()}")
                }
            }

            override fun onFailure(
                call: Call<Group>,
                t: Throwable
            ) {
                Log.e("CreateGroup", "Error: ${t.message}")
                t.printStackTrace()
            }
        })
    }
}