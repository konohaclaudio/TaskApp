package com.example.taskapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentRegisterBinding
import com.example.taskapp.utils.initToolbar
import com.example.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        auth = Firebase.auth

        initListerner()

    }


    private fun initListerner() {
        _binding?.btnRegister?.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        var email = binding.edtEmail.text.toString().trim()
        var password = binding.edtPassword.text.toString().trim()
        var text = binding.edtPassword.text.toString().trim()
        var age = binding.edtAge.text.toString().trim()
        var name = binding.edtName.text.toString().trim()

        if (email.isNotEmpty()) {

            if (password.length < 6) {
                binding.edtPassword.setError("")
                showBottomSheet(message = getString(R.string.msg_toastpassword_lenght))
            }
            if (password.isNotEmpty()) {

                binding.progressBar.isVisible = true
                registerUser(email, password)
                Toast.makeText(requireContext(), R.string.msg_toastok, Toast.LENGTH_SHORT).show()

            } else {
                showBottomSheet(message = getString(R.string.msg_toast_password))

            }
        } else {
            showBottomSheet(message = getString(R.string.msg_toast_email))
        }


    }


    private fun clearFields() {
        binding.edtEmail.setText("")
        binding.edtName.setText("")
        binding.edtPassword.setText("")
        binding.edtAge.setText("")
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    findNavController().navigate(R.id.action_global_homeFragment)

                } else {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT)
                        .show()

                }
            }
    }

    private fun verfica() {
        if (binding.edtEmail.text.isNullOrBlank()) {
            binding.edtEmail.setError("Write the Email")
        }


    }//    private fun verifyFields() {
//
//        if(binding.edtEmail.text.isNullOrBlank()) {
//            binding.edtEmail.setError ("Write the Email")
//        }
//        if(binding.edtName.text!!.isEmpty()) {
//            binding.edtName.setError("Write the Name")
//        }
//        if(binding.edtPassword.text.isEmpty()) {
//            binding.edtPassword.setError("Write the Password")
//        }
//        if(binding.edtAge.text.isEmpty()) {
//            binding.edtAge.setError("Write the Age")
//        }

//        if (binding.edtEmail.text.isEmpty() ||binding.edtName.text.isEmpty() || binding.edtPassword.text.isEmpty() || binding.edtAge.text.isEmpty())
//        {
//            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
//        }
//

}

