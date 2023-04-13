package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.adapter.TaskAdapter
import com.example.taskapp.data.Status
import com.example.taskapp.data.Task
import com.example.taskapp.databinding.FragmentDoingBinding


class DoingFragment : Fragment() {
    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter : TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentDoingBinding.inflate (inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView(getTask())
    }

    private fun initListeners(){
        binding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        } }

    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(requireContext(),taskList) {
                task, option ->
            optionSelected(task, option)
        }
        binding.rvTask.layoutManager = LinearLayoutManager (requireContext())
        binding.rvTask.setHasFixedSize(true)
        binding.rvTask.adapter = taskAdapter

    }


    private fun optionSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                Toast.makeText(requireContext(), "Removendo ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(requireContext(), "Editando ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_NEXT -> {
                Toast.makeText(requireContext(), "Next ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_BACK -> {
                Toast.makeText(requireContext(), "Back ${task.description}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTask() = listOf(
        Task("0", "TESTANDO", Status.DOING),
        Task("1", "Validar informaçações na tela de login", Status.DOING),
        Task("2", "Adicionar nova funcionaldade no app", Status.DOING),
        Task("3", "Salvar token no localmente", Status.DOING),
        Task("4", "Criar funcionalidade de logout no app", Status.DOING),
    )



}