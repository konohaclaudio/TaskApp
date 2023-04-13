package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentFormTaskBinding
import com.example.taskapp.utils.initToolbar
import com.example.taskapp.utils.showBottomSheet


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
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
        _binding =  FragmentFormTaskBinding.inflate (inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)
    }



    private fun initListerner(){

        _binding?.btnSave?.setOnClickListener{
            validateData()

        }

    }

    private fun validateData(){
        var description = binding.edtDescription.text.toString().trim()

        if (description.isNotEmpty()){

            Toast.makeText(requireContext(), R.string.msg_toastok, Toast.LENGTH_SHORT).show()
        }
        else {
            showBottomSheet(message = getString(R.string.description))
        }
    }


}