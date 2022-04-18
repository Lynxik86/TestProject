package com.example.android_dev.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_dev.R.id.action_ThirdFragment_to_SecondFragment
import com.example.android_dev.databinding.FragmentThirdBinding
import com.example.android_dev.viewmodel.RegistryViewModel

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var registryViewModel: RegistryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registryViewModel = ViewModelProvider(this)[RegistryViewModel::class.java]

        binding.formRegister.setOnClickListener {

            val firstname = binding.firstNameEdit.text.toString()
            val lastname = binding.lastNameEdit.text.toString()
            val email = binding.mailEdit.text.toString()
            val phone = binding.poneEdit.text.toString()
            registryViewModel.writeRegistryData(requireContext().applicationContext,
                firstname, lastname, email, phone)
            findNavController().navigate(action_ThirdFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // _binding = null
    }

}