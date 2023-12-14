package pl.edu.agh.utp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.utp.R
import pl.edu.agh.utp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Initialize views
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}