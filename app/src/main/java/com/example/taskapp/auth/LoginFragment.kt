package com.example.taskapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentLoginBinding
import com.example.taskapp.utils.showBottomSheet


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListerner()

    }

    private fun validateData(){
        var email = binding.edtEmailFragmentLogin.text.toString().trim()
        var password = binding.edtSenhaFragmentLogin.text.toString().trim()

        if (email.isNotEmpty()){
            if(password.isNotEmpty()){

                Toast.makeText(requireContext(), R.string.msg_toastok, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_global_homeFragment)
            }
            else {
                showBottomSheet(message = getString(R.string.msg_toastsenha))
            }}
        else {
            showBottomSheet(message = getString(R.string.msg_toastemail))
        } }


    private fun initListerner(){

        _binding?.btnCreate?.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        _binding?.btnRecovery?.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }

        _binding?.btnLogin?.setOnClickListener{

            validateData()
        }
    }
}
