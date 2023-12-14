package pl.edu.agh.utp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import pl.edu.agh.utp.manager.UserManager
import pl.edu.agh.utp.model.User

private const val ARG_NAME = "name"
private const val ARG_EMAIL = "email"
private const val ARG_PASSWORD = "password"

class ProfileFragment : Fragment() {
    private var name: String? = "TestName"
    private var email: String? = "test@mail.com"
    private var password: String? = "TestPassword"
    private var userManager: UserManager? = null
    private var User: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            email = it.getString(ARG_EMAIL)
            password = it.getString(ARG_PASSWORD)
            userManager = UserManager(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<TextView>(R.id.textViewName)?.text = name
        view.findViewById<TextView>(R.id.textViewEmail)?.text = email
        view.findViewById<TextView>(R.id.textViewPassword)?.text = password
        view.findViewById<Button>(R.id.logoutButton)?.setOnClickListener {
            userManager?.logOut()
            User = userManager?.getUser()
            if (User == null) {
                Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }

        }

        view.findViewById<ImageView>(R.id.editIconName)?.setOnClickListener {
            showEditDialog("Name") { editedText ->
                view.findViewById<TextView>(R.id.textViewName)?.text = editedText
            }
        }

        view.findViewById<ImageView>(R.id.editIconPassword)?.setOnClickListener {
            showEditDialog("Password") { editedText ->
                view.findViewById<TextView>(R.id.textViewPassword)?.text = editedText
            }
        }

        view.findViewById<ImageView>(R.id.editIconEmail)?.setOnClickListener {
            showEditDialog("Email") { editedText ->
                view.findViewById<TextView>(R.id.textViewEmail)?.text = editedText
            }
        }

        return view
    }

    private fun showEditDialog(title: String, onTextEdited: (String) -> Unit) {
        val editText = EditText(requireContext())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit $title")
            .setView(editText)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val editedText = editText.text.toString().trim()

                val isValidEmail = if (title.equals("Email", ignoreCase = true)) {
                    android.util.Patterns.EMAIL_ADDRESS.matcher(editedText).matches()
                } else {
                    true
                }

                if (editedText.isNotEmpty() && isValidEmail) {
                    onTextEdited.invoke(editedText)
                    dialog.dismiss()
                    Log.d("ProfileFragment", "Name: $name, Email: $email, Password: $password")
                } else {
                    val errorMessage = when {
                        editedText.isEmpty() -> "Please enter a valid $title"
                        !isValidEmail -> "Please enter a valid email address"
                        else -> "Invalid input"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, email: String, password: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_EMAIL, email)
                    putString(ARG_PASSWORD, password)
                }
            }
    }
}