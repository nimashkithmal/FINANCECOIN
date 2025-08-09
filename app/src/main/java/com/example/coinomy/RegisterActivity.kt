package com.example.coinomy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnRegister = findViewById(R.id.btn_register)

        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidUsername(username)) {
            Toast.makeText(this, "Username must be at least 6 characters, contain only letters and numbers, and include at least 2 digits", Toast.LENGTH_LONG).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return
        }

        // Save credentials
        val prefs = PreferenceManager.getInstance(this)
        prefs.setUserName(username)
        prefs.setUserPassword(password)

        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9]{6,}$")
        val digitCount = username.count { it.isDigit() }
        return regex.matches(username) && digitCount >= 2
    }
}
