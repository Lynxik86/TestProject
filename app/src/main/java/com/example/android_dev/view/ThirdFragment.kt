package com.example.android_dev.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_dev.R.id.action_ThirdFragment_to_SecondFragment
import com.example.android_dev.databinding.FragmentThirdBinding
import com.example.android_dev.util.AppUtils
import com.example.android_dev.viewmodel.RegistryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    lateinit var registryViewModel: RegistryViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        editData()

        binding.formRegister.setOnClickListener { registerButton ->
            AppUtils.hideKeyboard(requireContext(), registerButton)

            validateAndSaveData()
        }
    }

    private fun editData() {
        binding.firstNameEdit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && registryViewModel.checkFirstNameByRegex(binding.firstNameEdit.text.toString())) {
                binding.firstNameEdit.isEnabled = false
            }
        }

        binding.lastNameEdit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && registryViewModel.checkLastNameByRegex(binding.lastNameEdit.text.toString())) {
                binding.lastNameEdit.isEnabled = false
            }
        }

        binding.mailEdit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && registryViewModel.checkEmailByRegex(binding.mailEdit.text.toString())) {
                binding.mailEdit.isEnabled = false
            }
        }

        binding.poneEdit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && registryViewModel.checkPhoneByRegex(binding.poneEdit.text.toString())) {
                binding.poneEdit.isEnabled = false
            }
        }
    }

    private fun validateAndSaveData() {
        val firstname = binding.firstNameEdit.text.toString()
        val lastname = binding.lastNameEdit.text.toString()
        val email = binding.mailEdit.text.toString()
        val phone = binding.poneEdit.text.toString()

        if (registryViewModel.checkAllTextByRegex(firstname, lastname, email, phone)) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                registryViewModel.writeRegistryData(firstname, lastname, email, phone)
            }
            findNavController().navigate(action_ThirdFragment_to_SecondFragment)
        } else {
            Toast.makeText(requireContext(), "Неправильные данные!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

