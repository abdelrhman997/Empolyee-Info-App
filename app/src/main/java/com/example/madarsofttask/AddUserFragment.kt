package com.example.madarsofttask

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.madarsofttask.databinding.FragmentAddUserBinding


class AddUserFragment : Fragment() {

    private val viewModel: UsersViewModel by activityViewModels {
        UserViewModelFactory(
            (activity?.application as UsersApplication).database
                .userDao()
        )
    }
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val genders = resources.getStringArray(R.array.gender_types)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)

        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        return binding.root
    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.userName.text.toString(),
            binding.userAge.text.toString(),
            binding.userJobTitle.text.toString(),
            binding.autoCompleteTextView.text.toString()
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewUser(binding.userName.text.toString(), binding.userAge.text.toString(), binding.userJobTitle.text.toString(), binding.autoCompleteTextView.text.toString())
            val action = AddUserFragmentDirections.actionAddUserFragmentToUserListFragment()
            findNavController().navigate(action)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.saveAction.setOnClickListener {
                addNewItem()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
