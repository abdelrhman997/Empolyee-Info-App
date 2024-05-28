package com.example.madarsofttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.madarsofttask.databinding.UserListFragmentBinding


class UserListFragment : Fragment() {
    private val viewModel: UsersViewModel by activityViewModels {
        UserViewModelFactory(
            (activity?.application as UsersApplication).database.userDao()
        )
    }

    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserListAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.allUsers.observe(this.viewLifecycleOwner) { users ->
            users.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToAddUserFragment()
            this.findNavController().navigate(action)
        }
    }
}
