package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.R
import com.example.taskapp.adapter.ViewPagerAdapter
import com.example.taskapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
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

        _binding =  FragmentHomeBinding.inflate (inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabs()
    }

    private fun initTabs(){
        val pageAdapter = ViewPagerAdapter(requireActivity())
        binding.ViewPager.adapter = pageAdapter

        pageAdapter.addFragment(ToDoFragment(), R.string.status_task_todo)
        pageAdapter.addFragment(DoingFragment(), R.string.status_task_doing)
        pageAdapter.addFragment(DoneFragment(), R.string.status_task_done)

        binding.ViewPager.offscreenPageLimit = pageAdapter.itemCount

        TabLayoutMediator(binding.tabs, binding.ViewPager) {
            tab, position ->
            tab.text = getString(pageAdapter.getTitle(position))
        }.attach()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}