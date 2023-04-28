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
import com.example.taskapp.databinding.FragmentLoginBinding
import com.example.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
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
         auth = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListerner()

    }

    private fun validateData() {
        val email = binding.edtEmailFragmentLogin.text.toString().trim()
        val password = binding.edtSenhaFragmentLogin.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                showBottomSheet(message = getString(R.string.msg_toast_password))
                 binding.edtSenhaFragmentLogin.setError("")
            }
        } else {
            binding.edtEmailFragmentLogin.setError("")
            showBottomSheet(message = getString(R.string.msg_toast_email))
        }
    }


    private fun initListerner() {

        _binding?.btnCreate?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        _binding?.btnRecovery?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }

        _binding?.btnLogin?.setOnClickListener {

            validateData()
        }
    }

    private fun loginUser(email : String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), R.string.msg_toastok, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_global_homeFragment)

                } else {

                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()

                }
            }



    }

}
