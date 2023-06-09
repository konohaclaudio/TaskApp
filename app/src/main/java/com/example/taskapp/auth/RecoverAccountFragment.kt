package com.example.taskapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentRecoverAccountBinding
import com.example.taskapp.utils.FirebaseHelper
import com.example.taskapp.utils.initToolbar
import com.example.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecoverAccountFragment : Fragment() {
    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        initListerner()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListerner() {
        _binding?.btnRecover?.setOnClickListener {
            validateData()

        }
    }


    private fun validateData() {
        var email = binding.edtRecoverEmail.text.toString().trim()
        if (email.isNotEmpty()) {

            binding.progressBar.isVisible = true
            recoverAccountUser(email)

            Toast.makeText(requireContext(), R.string.msg_toastok, Toast.LENGTH_SHORT).show()
        } else {
            showBottomSheet(message = getString(R.string.reccouver_account_email))
        }


    }

    private fun recoverAccountUser(email: String) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showBottomSheet(message = getString(R.string.text_message_recover_account_fragment))
            } else {
                binding.progressBar.isVisible = false
                showBottomSheet(
                    message = getString(FirebaseHelper.validError(task.exception?.message.toString()))
                )
            }
        }
    }


}