package com.example.android_dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android_dev.R
import com.example.android_dev.databinding.FragmentSecondBinding
import com.example.android_dev.util.AppUtils
import com.example.android_dev.viewmodel.RegistryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
   /*  private lateinit var registryViewModel: RegistryViewModel*/

    private val registryViewModel: RegistryViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // private var numberOfRemainingLoginAttempts = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //chuckViewModel = ViewModelProvider(this)[ChuckViewModel::class.java]
        //registryViewModel = ViewModelProvider(this)[RegistryViewModel::class.java]

        setupViews()
        observeViews()
    }

    /* private fun deleteDB() {
         chuckViewModel.coroutineDeleteChuck()
     }*/


    private fun observeViews() {
        registryViewModel.successfulLogin.observe(viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                registryViewModel.loginComplete()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }

        registryViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
        }
    }

    /*  private fun clearForm() {
          with(binding){
              editUserLogin.text.clear()
              editUserPassword.text.clear()
          }
      }*/

    private fun setupViews() {
        binding.buttonLogin.setOnClickListener { loginButton ->
            AppUtils.hideKeyboard(requireContext(), loginButton)
            val firstname = binding.editUserLogin.text.toString()
            val lastname = binding.editUserPassword.text.toString()
            registryViewModel.checkCredentials(firstname, lastname)
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }
    }

    //  fun checkEnter(){


    /*  if (binding.editUserLogin.text.toString()== "admin" && binding.editUserPassword.text.toString() == "admin") {
          Toast.makeText(
              requireContext(),
              "Вход выполнен!",
              Toast.LENGTH_LONG
          ).show()
          findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
      } else if (binding.editUserLogin.text.isNullOrEmpty() || binding.editUserPassword.text.isNullOrEmpty()) {
          Toast.makeText(
              requireContext(),
              "Пустое поле!",
              Toast.LENGTH_LONG
          ).show()
      } else {
          Toast.makeText(
              requireContext(),
              "Неправильные данные!",
              Toast.LENGTH_LONG
          ).show()

          numberOfRemainingLoginAttempts--
          // Делаем видимыми текстовые поля, указывающие на количество оставшихся попыток:
          binding.numberOfAttempts.visibility = VISIBLE;
          binding.attempts.visibility = VISIBLE;
          binding.numberOfAttempts.text = numberOfRemainingLoginAttempts.toString()
      }

      if (numberOfRemainingLoginAttempts <= 0) {
          // Когда выполнено 3 безуспешных попытки залогиниться,
          // делаем видимым текстовое поле с надписью, что все пропало и выставляем
          // кнопке настройку невозможности нажатия setEnabled(false):
          binding.editUserLogin.isEnabled = false;
          binding.loginLocked.visibility = VISIBLE;
          binding.loginLocked.setBackgroundColor(Color.RED);
          binding.loginLocked.text = "Вход заблокирован!!!";
          binding.numberOfAttempts.visibility = View.INVISIBLE;
          binding.attempts.visibility = View.INVISIBLE;
      }*/


    //  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //deleteDB()

    }
}