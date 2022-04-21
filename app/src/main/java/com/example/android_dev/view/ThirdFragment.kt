package com.example.android_dev.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_dev.R.id.action_ThirdFragment_to_SecondFragment
import com.example.android_dev.databinding.FragmentThirdBinding
import com.example.android_dev.viewmodel.RegistryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

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

        editData()
        registryData()

        findNavController().navigate(action_ThirdFragment_to_SecondFragment)

    }

    private fun editData() {

        val firstname = Regex("^[a-zA-Z][a-zA-Z0-9]{1,10}$")
        val lastname = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#\$%^&*]{6,}$")
        val mail = Regex("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}\$")
        val tel = Regex("^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}\$")

        binding.firstNameEdit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && (binding.firstNameEdit.text.matches(firstname))) {
                binding.firstNameEdit.isEnabled = false;
            }
        }

        binding.lastNameEdit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && (binding.lastNameEdit.text.matches(lastname))) {
                binding.lastNameEdit.isEnabled = false;
            }
        }

        binding.mailEdit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && (binding.mailEdit.text.matches(mail))) {
                binding.mailEdit.isEnabled = false;
            }
        }

        binding.poneEdit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && (binding.poneEdit.text.matches(tel))) {
                binding.poneEdit.isEnabled = false;
            }
        }
    }

    private fun registryData() {
        lateinit var registryViewModel: RegistryViewModel
        registryViewModel = ViewModelProvider(this)[RegistryViewModel::class.java]

        val firstname = binding.firstNameEdit.text.toString()
        val lastname = binding.lastNameEdit.text.toString()
        val email = binding.mailEdit.text.toString()
        val phone = binding.poneEdit.text.toString()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            registryViewModel.writeRegistryData(
                requireContext().applicationContext,
                firstname, lastname, email, phone
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}

