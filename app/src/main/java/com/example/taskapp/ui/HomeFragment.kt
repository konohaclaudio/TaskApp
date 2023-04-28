package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.adapter.ViewPagerAdapter
import com.example.taskapp.databinding.FragmentHomeBinding
import com.example.taskapp.utils.showBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
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

        _binding =  FragmentHomeBinding.inflate (inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = Firebase.auth
        initListeners()
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

    private fun initListeners() {
        binding.btnLogout.setOnClickListener{
            showBottomSheet(R.string.text_title_dialog_confirm_logout,
                R.string.text_button_dialog_confirm_logout,
                getString(R.string.text_message_dialog_confirm_logout),
                {  auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_authentication) },)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}