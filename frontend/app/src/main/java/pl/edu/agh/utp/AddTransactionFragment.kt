package pl.edu.agh.utp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import pl.edu.agh.utp.api.ApiObject
import pl.edu.agh.utp.manager.UserManager
import pl.edu.agh.utp.model.PersonInfo
import pl.edu.agh.utp.model.Transaction
import pl.edu.agh.utp.model.TransactionRequest
import pl.edu.agh.utp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class AddTransactionFragment(private val groupId: Long) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userManager = UserManager(requireContext())
    }


    private var people: MutableMap<Long, PersonInfo> = mutableMapOf(
        11L to PersonInfo("Mike", false),
        12L to PersonInfo("Anna", false)
    )

    private var category: String = ""
    private var description: String = ""
    private var amount: Double = 0.0
    private lateinit var userManager: UserManager
    private var user: User? = null
    private var debtsUserIds: List<Long> = listOf()

    private lateinit var descriptionEditText: EditText
    private lateinit var paymentEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var addExpenseButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        user = userManager.getUser()
        fetchGroupUsers(groupId)
        people.remove(user?.id)

        val view = inflater.inflate(R.layout.fragment_add_transaction, container, false)
        val peopleLinearLayout: ViewGroup = view.findViewById(R.id.linearLayoutPeople)

        for ((id, PersonData) in people) {
            val checkBox = CheckBox(requireContext())
            checkBox.text = PersonData.name
            checkBox.isChecked = PersonData.isSelected
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                people[id]?.isSelected = isChecked
            }
            peopleLinearLayout.addView(checkBox)
        }

        descriptionEditText = view.findViewById(R.id.editTextDescription)
        paymentEditText = view.findViewById(R.id.editTextPayment)
        categoryEditText = view.findViewById(R.id.editCategory)
        addExpenseButton = view.findViewById(R.id.addExpenseButton)

        addExpenseButton.setOnClickListener { addExpenseButtonClicked(it) }

        return view
    }

    fun displayErrorToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
    fun validatePeople(): Boolean {
        if (people.isEmpty() || !people.any { it.value.isSelected }) {
            val errorMessage = "Select at least one person to your expense."
            displayErrorToast(errorMessage)
            return false
        } else {
            return true
        }
    }
    fun validateAmount(): Boolean {
        if (amount <= 0.0) {
            displayErrorToast("Amount cannot be empty.")
            return false
        }
        return true
    }
    fun validateDescription(): Boolean {
        if (description.isNullOrBlank()) {
            displayErrorToast("Description cannot be empty.")
            return false
        }
        return true
    }
    fun validateCategory(): Boolean {
        if (category.isNullOrBlank()) {
            displayErrorToast("Category cannot be empty.")
            return false
        }
        return true
    }
    fun validate(): Boolean {
        description = descriptionEditText.text.toString()
        amount = paymentEditText.text.toString().toDoubleOrNull() ?: 0.0
        category = categoryEditText.text.toString()
        return validateDescription() && validateAmount() && validateCategory()  &&  validatePeople()
    }

    private fun navigateToTransactionsFragment() {
        val fragment = TransactionsFragment(groupId)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addExpenseButtonClicked(view: View) {
        if(!validate()){
            return
        }

        val todaysDate: LocalDate = LocalDate.now()
        debtsUserIds = people.filter { it.value.isSelected }.map { it.key }

        val TransactionRequest = TransactionRequest(
            description,
            todaysDate.toString(),
            category,
            amount,
            user?.id!!,
            debtsUserIds
        )

        addTransaction(groupId, TransactionRequest) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Expense added successfully", Toast.LENGTH_SHORT).show()
                navigateToTransactionsFragment()
            } else {
                Toast.makeText(requireContext(), "Something went worng. Expense not added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun preparePeopleHashMap(users: List<User>) {
        for (user in users) {
            people[user.id] = PersonInfo(user.name, false)
        }
    }

    private fun fetchGroupUsers(groupId: Long) {
        ApiObject.instance.getUsersFromGroup(groupId).enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if (response.isSuccessful) {
                    var users = response.body()!!
                    Log.d("AddTransactionFragment", "Users: $users")
                    preparePeopleHashMap(users)
                } else {
                    Log.d("AddTransactionFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("AddTransactionFragment", "Error: ${t.message}")
            }
        })
    }

    private fun addTransaction(groupId: Long, transactionRequest: TransactionRequest, callback: (Boolean) -> Unit) {
        ApiObject.instance.addTransaction(groupId, transactionRequest)
            .enqueue(object : Callback<Transaction> {
                override fun onResponse(
                    call: Call<Transaction>,
                    response: Response<Transaction>
                ) {
                    if (response.isSuccessful) {
                        val addedTransaction = response.body()
                        Log.d("AddTransactionFragment", "Transaction added: $addedTransaction")
                        callback(true)
                    } else {
                        Log.d("AddTransactionFragment", "Error: ${response.code()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<Transaction>, t: Throwable) {
                    Log.d("AddTransactionFragment", "Error: ${t.message}")
                    callback(false)
                }
            })
    }
}