package com.example.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.example.taskapp.utils.FirebaseHelper


class FormTaskFragment : BaseFragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!


    private lateinit var task: Task
    private var newTask: Boolean = true
    private var status: Status = Status.TODO
    private val args: FormTaskFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels()

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

        getArgs()
        initListerner()
    }

    private fun getArgs() {
        args.task.let { it ->
            if (it != null) {
                this.task = it

                configTask()
            }
        }
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

            hideKeyboard()

            binding.progressBar.isVisible = true

            if (newTask) {
                task = Task()
            }
            task.description = description
            task.status = status
            saveTask()


        } else {
            showBottomSheet(message = getString(R.string.description))
        }
    }

    private fun saveTask() {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {

                    Toast.makeText(requireContext(), R.string.msg_toast_save, Toast.LENGTH_SHORT)
                        .show()

                    if (newTask) { //nwova

                        findNavController().popBackStack()

                    } else { //editando

                        viewModel.setUpdateTask(task)
                        Toast.makeText(
                            requireContext(),
                            R.string.text_update_task_formtask_fragment,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        binding.progressBar.isVisible = false
                        findNavController().popBackStack()


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


    private fun configTask() {
        newTask = false
        status = task.status
        binding.textTitleToolbar.text = ("Editando: " + task.description)

        binding.edtDescription.setText(task.description)

        setStatus()

    }


    private fun setStatus() {
        val id = when (task.status) {
            Status.TODO -> R.id.rbTodo
            Status.DOING -> R.id.rbDoing
            else -> R.id.rbDone
        }

    }
}