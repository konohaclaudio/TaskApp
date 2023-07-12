package com.example.taskapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.adapter.TaskAdapter
import com.example.taskapp.model.Status
import com.example.taskapp.model.Task
import com.example.taskapp.databinding.FragmentDoingBinding
import com.example.taskapp.utils.FirebaseHelper
import com.example.taskapp.utils.showBottomSheet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DoingFragment : Fragment() {

    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
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
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()
        getTask()
    }
    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(action)

        }

        observeViewModel()


    }

    private fun observeViewModel() {
        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->
            if (updateTask.status == Status.DOING) {
//                Armazena a lista atual do apater
                val oldList = taskAdapter.currentList
//            Gera uma nova lista a partir da lista antiga já com a tarefa atualizada
                val newList = oldList.toMutableList().apply {
                    find { it.id == updateTask.id }?.description == updateTask.description
                }
//                Armazena a posição da tarefa a ser aatualizada na lista
                val position = newList.indexOfFirst { it.id == updateTask.id }

//               Envia a lista atualizada para o adpater
                taskAdapter.submitList(newList)

//                Atualiza a tarefa pela posição do adapter
                taskAdapter.notifyItemChanged(position)

            }
        }
    }

    private fun initRecyclerView() {
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }

        with(binding.rvTask) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }

//        binding.rvTask.layoutManager = LinearLayoutManager (requireContext())
//        binding.rvTask.setHasFixedSize(true)
//        binding.rvTask.adapter = taskAdapter
    }

    private fun optionSelected(task: Task, option: Int) {
        when (option) {

            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(
                    R.string.text_title_dialog_delete,
                    R.string.text_button_dialog_confirm,
                    getString(R.string.text_message_dialog_delete),
                    { deleteTask(task) }
                )

            }
            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(requireContext(), "Editar tarefa ${task.description}", Toast.LENGTH_SHORT)
                    .show()
                val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes da tarefa${task.description}", Toast.LENGTH_SHORT)
                    .show()
            }
            TaskAdapter.SELECT_NEXT -> {
                task.status = Status.DONE
                updateTask(task)

            }
            TaskAdapter.SELECT_BACK -> {
                task.status = Status.TODO
                updateTask(task)

            }
        }
    }

    private fun getTask() {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()
                    for (ds in snapshot.children) {
                        val task = ds.getValue(Task::class.java) as Task
                        if (task.status == Status.DOING)
                            taskList.add(task)
                    }

                    binding.progressBar.isVisible = false
                    listEmpty(taskList)

                    taskList.reverse()
                    taskAdapter.submitList(taskList)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("INFOTESTE", "onCancelled")

                }
            })
    }

    private fun listEmpty(tasklist: List<Task>) {
        binding.textInfo.text = if (tasklist.isEmpty()) {
            getString(R.string.text_list_task_empty)
        } else {
            ""
        }

    }

    private fun updateTask(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.text_list_task_save,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

    private fun deleteTask(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.text_list_task_delete,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()

                }
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}