package com.example.coinomy

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.coinomy.databinding.DialogEditProfileBinding
import com.example.coinomy.databinding.FragmentSettingsBinding
import com.example.coinomy.storage.FileStorageManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var transactionRepository: TransactionRepository

    private val gson = Gson()

    val backupLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
        uri?.let { saveBackupToUri(it) }
    }

    val restoreLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { readBackupFromUri(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("coinomy_prefs", Context.MODE_PRIVATE)
        transactionRepository = TransactionRepository(requireContext())

        childFragmentManager.beginTransaction()
            .replace(R.id.settings_container, SettingsPreferenceFragment())
            .commit()

        loadUserProfile()

        binding.editProfileButton.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun loadUserProfile() {
        val name = sharedPreferences.getString("user_name", "User Name") ?: "User Name"
        val email = sharedPreferences.getString("user_email", "email@example.com") ?: "email@example.com"
        binding.profileName.text = name
        binding.profileEmail.text = email
    }

    private fun showEditProfileDialog() {
        val dialogBinding = DialogEditProfileBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.nameInput.setText(sharedPreferences.getString("user_name", ""))
        dialogBinding.emailInput.setText(sharedPreferences.getString("user_email", ""))

        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.saveButton.setOnClickListener {
            val name = dialogBinding.nameInput.text.toString().trim()
            val email = dialogBinding.emailInput.text.toString().trim()

            var isValid = true

            if (name.isEmpty()) {
                dialogBinding.nameInputLayout.error = "Name cannot be empty"
                isValid = false
            } else {
                dialogBinding.nameInputLayout.error = null
            }

            if (email.isNotEmpty() && !isValidEmail(email)) {
                dialogBinding.emailInputLayout.error = "Please enter a valid email"
                isValid = false
            } else {
                dialogBinding.emailInputLayout.error = null
            }

            if (isValid) {
                sharedPreferences.edit().apply {
                    putString("user_name", name)
                    putString("user_email", email)
                    apply()
                }

                loadUserProfile()
                dialog.dismiss()
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun saveBackupToUri(uri: Uri) {
        try {
            val userPrefs = sharedPreferences
            val appPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val transactions = transactionRepository.getAllTransactions()

            val backupMap = mapOf(
                "user_name" to userPrefs.getString("user_name", ""),
                "user_email" to userPrefs.getString("user_email", ""),
                "currency" to appPrefs.getString("currency", "LKR"),
                "theme_mode" to appPrefs.getBoolean("theme_mode", false),
                "notifications" to appPrefs.getBoolean("notifications", true),
                "transactions" to transactions
            )

            val backupJson = gson.toJson(backupMap)

            requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(backupJson.toByteArray())
                Toast.makeText(requireContext(), "Backup saved successfully", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Backup failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun readBackupFromUri(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val content = inputStream?.bufferedReader()?.use { it.readText() } ?: return

            val type = object : TypeToken<Map<String, Any>>() {}.type
            val dataMap: Map<String, Any> = gson.fromJson(content, type)

            sharedPreferences.edit().apply {
                putString("user_name", dataMap["user_name"] as? String)
                putString("user_email", dataMap["user_email"] as? String)
                apply()
            }

            PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().apply {
                putString("currency", dataMap["currency"] as? String ?: "LKR")
                putBoolean("theme_mode", (dataMap["theme_mode"] as? Boolean) ?: false)
                putBoolean("notifications", (dataMap["notifications"] as? Boolean) ?: true)
                apply()
            }

            val transactionsJson = gson.toJson(dataMap["transactions"])
            val transactionListType = object : TypeToken<List<TransactionData>>() {}.type
            val transactions: List<TransactionData> = gson.fromJson(transactionsJson, transactionListType)

            val storageManager = FileStorageManager.getInstance(requireContext())
            storageManager.clearAllTransactions()
            storageManager.saveTransactions(transactions)

            loadUserProfile()

            Toast.makeText(requireContext(), "Restore completed", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Restore failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class SettingsPreferenceFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            findPreference<Preference>("backup_data")?.setOnPreferenceClickListener {
                (parentFragment as? SettingsFragment)?.backupLauncher?.launch("coinomy_backup.txt")
                true
            }

            findPreference<Preference>("restore_data")?.setOnPreferenceClickListener {
                (parentFragment as? SettingsFragment)?.restoreLauncher?.launch(arrayOf("text/plain"))
                true
            }

            findPreference<Preference>("logout")?.setOnPreferenceClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes") { _, _ ->
                        val prefs = requireActivity().getSharedPreferences("coinomy_prefs", Context.MODE_PRIVATE)
                        prefs.edit().putBoolean("is_logged_in", false).apply()
                        val intent = Intent(requireContext(), login::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
                true
            }
        }
    }
}
