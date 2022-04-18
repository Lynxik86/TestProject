package com.example.android_dev.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_dev.RecyclerChuckAdapter
import com.example.android_dev.databinding.FragmentFirstBinding
import com.example.android_dev.recyclerview.RecyclerJokeAdapter
import com.example.android_dev.viewmodel.ChuckViewModel
import com.example.android_dev.viewmodel.JokeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers as CoroutinesDispatchers


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), CoroutineScope {
    private var firstFragmentJob: Job? = null
        get() {
            if (field == null) {
                field = Job()
                field!!.start()
            }
            return field
        }

    override val coroutineContext: CoroutineContext
        get() = CoroutinesDispatchers.Default + firstFragmentJob!!

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var chuckViewModel: ChuckViewModel
    private lateinit var jokesViewModel: JokeViewModel

    //вызывается в момент создания View и в нём инится сам layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    //вызывается после того как layout проинициализирован.тут уже привязываем логику к вьюхам.
    // Это гарантирует что вьюха создана, и никакие null pointer'ы падать не будут.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  val nameObserver = Observer<List<ChuckResult>> { chuck ->
        // Update the UI, in this case, a TextView.
        binding.textviewFirst.text =  chuck[0].id
        }*/

        // Get the view model
        chuckViewModel = ViewModelProvider(this)[ChuckViewModel::class.java]
        jokesViewModel = ViewModelProvider(this)[JokeViewModel::class.java]

        chuckViewModel.coroutineGetChuck(requireContext().applicationContext)

        // Specify layout for recycler view
        val linearLayoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false
        )

        binding.recyclerView.layoutManager = linearLayoutManager

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        binding.buttonSecond.setOnClickListener {
         /*   chuckViewModel.chuckId.observe(viewLifecycleOwner, Observer { id ->
                binding.textviewFirst.text = id
            })*/

            chuckViewModel.allChucks.observe(viewLifecycleOwner, Observer { chucks ->
                // Data bind the recycler view
                binding.recyclerView.adapter = RecyclerChuckAdapter(chucks)
            })
        }

        binding.buttonFirst.setOnClickListener {
            //запуск новой сопрограммы в фоне
            //сопрограммы - это легковесные потоки. Они запускаются с помощью билдера сопрограмм launch в контексте некоторого CoroutineScope. В примере выше мы запускаем новую сопрограмму в GlobalScope.
            // Это означает, что время жизни новой сопрограммы ограничено только временем жизни всего приложения.
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    binding.textviewFirst.text =
                        jokesViewModel.getJokes(requireContext().applicationContext).joke
                }
            }

            jokesViewModel.allJokes.observe(viewLifecycleOwner, Observer { jokes ->
                // Data bind the recycler view

                binding.recyclerView.adapter = RecyclerJokeAdapter(jokes)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firstFragmentJob?.cancel()
        firstFragmentJob = null
        _binding = null

    }
}