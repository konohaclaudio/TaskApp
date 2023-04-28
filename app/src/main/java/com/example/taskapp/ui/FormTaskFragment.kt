package com.example.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentFormTaskBinding
import com.example.taskapp.model.Status
import com.example.taskapp.model.Task
import com.example.taskapp.utils.initToolbar
import com.example.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!
    private var newTask: Boolean = true
    private var status: Status = Status.TODO

    private val args: FormTaskFragmentArgs by navArgs()

    private lateinit var task: Task
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)
        binding.progressBar.isVisible = false
        reference = Firebase.database.reference
        auth = Firebase.auth

        initListerner()
    }


    private fun initListerner() {

        binding?.btnSave?.setOnClickListener {
            validateData()
        }

        binding.rgStatus.setOnCheckedChangeListener { _, id ->
            status = when (id) {
                R.id.rbTodo -> Status.TODO
                R.id.rbDoing -> Status.DOING
                else -> Status.DONE
            }
        }

    }

    private fun validateData() {

        var description = binding.edtDescription.text.toString().trim()

        if (description.isNotEmpty()) {

            binding.progressBar.isVisible = true

            if (newTask) task = Task()
            task.id = reference.database.reference.push().key ?: ""
            task.description = description
            task.status = status
            saveTask()


        } else {
            showBottomSheet(message = getString(R.string.description))
        }
    }

    private fun saveTask() {
        reference.child("task")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {

                    Toast.makeText(requireContext(), R.string.msg_toast_save, Toast.LENGTH_SHORT)
                        .show()

                    if (newTask) {

                        findNavController().popBackStack()

                    } else {
                        binding.progressBar.isVisible = false

                    }

                } else {

                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.error))
                }

            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun closefragment() {
        findNavController().navigate(
            R.id.action_formTaskFragment_to_homeFragment
        )

    }


}