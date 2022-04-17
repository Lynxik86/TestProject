package com.example.android_dev.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android_dev.R
import com.example.android_dev.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var numberOfRemainingLoginAttempts = 3
    private val regex = Regex("^[a-zA-Z][a-zA-Z0-9]{1,10}$")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            if (binding.editUserLogin.text.matches(regex) && binding.editUserPassword.text.toString() == "admin") {
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
            }
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}