package com.example.android_dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_dev.R
import com.example.android_dev.databinding.FragmentFirstBinding
import com.example.android_dev.ui.recyclerview.ButtonClickListener
import com.example.android_dev.ui.recyclerview.ItemClickListener
import com.example.android_dev.ui.recyclerview.RecyclerChuckAdapter
import com.example.android_dev.ui.recyclerview.RecyclerJokeAdapter
import com.example.android_dev.viewmodel.ChuckViewModel
import com.example.android_dev.viewmodel.JokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers as CoroutinesDispatchers




/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment(), CoroutineScope, ButtonClickListener, ItemClickListener {
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

    /* private lateinit var viewModelChuck: ChuckViewModel
     private lateinit var jokesViewModel: JokeViewModel*/

    private val chuckViewModel: ChuckViewModel by viewModels()
    private val jokesViewModel: JokeViewModel by viewModels()

    //вызывается в момент создания View и в нём инится сам layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    //вызывается после того как layout проинициализирован.тут уже привязываем логику к вьюхам.
    // Это гарантирует что вьюха создана, и никакие null поинтеры падать не будут.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the view model
        /* viewModelChuck = ViewModelProvider(this)[ChuckViewModel::class.java]
         jokesViewModel = ViewModelProvider(this)[JokeViewModel::class.java]*/

        val popupMenu = PopupMenu(requireContext(), binding.textviewFirst)
        popupMenu.inflate(R.menu.pop_up_menu)

        //deleteDBChuck()
        // deleteDBJoke()
        setupRecyclerView()
        observeViews()

        binding.textviewFirst.setOnClickListener {
            popUpUpdate(popupMenu)
            popupMenu.show()
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        binding.buttonAddChuck.setOnClickListener {
            chuckViewModel.coroutineGetChuck()
        }

        binding.buttonAddJokes.setOnClickListener {
            jokesViewModel.coroutineGetJoke()

            //запуск новой сопрограммы в фоне
            //сопрограммы - это легковесные потоки. Они запускаются с помощью билдера сопрограмм launch в контексте некоторого CoroutineScope. В примере выше мы запускаем новую сопрограмму в GlobalScope.
            // Это означает, что время жизни новой сопрограммы ограничено только временем жизни всего приложения.

            //  chuckViewModel.coroutineGetChuck(requireContext().applicationContext)
            /*     viewLifecycleOwner.lifecycleScope.launch {
                      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                         binding.textviewFirst.text =
                             jokesViewModel.getJokes(ContentProviderCompat.requireContext().applicationContext).joke
                     }
                 }*/
        }
    }

    private fun popUpUpdate(popupMenu: PopupMenu) {

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.updateChuck -> {
                    deleteDBChuck()
                    true
                }
                R.id.updateJoke -> {
                    deleteDBJoke()
                    true
                }
                else -> false
            }
        }
    }

    private fun deleteDBChuck() {
        chuckViewModel.coroutineDeleteChuck()
    }

    private fun deleteDBJoke() {
        jokesViewModel.coroutineDeleteJokes()
    }

    private fun setupRecyclerView() {
        // Specify layout for recycler view
        val linearLayoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false
        )
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    override fun onButtonClickListener(item: String) {
        //Toast.makeText(requireContext(),"Cell clicked", Toast.LENGTH_SHORT).show()
        deleteJokeResult(item)
        deleteChuckResult(item)
    }

    override fun onItemClickListener(item: String) {



        var action = FirstFragmentDirections.actionFirstFragmentToItemFragment(item)



        findNavController().navigate(action)
        findNavController().navigate(R.id.action_FirstFragment_to_ItemFragment)
        /*val fr = parentFragmentManager.beginTransaction()
        fr.replace(R.id.fr1, ItemFragment())
        fr.addToBackStack(null)
        fr.commit()*/

    }

    private fun deleteChuckResult(item: String) {
        chuckViewModel.coroutineDeleteChuckResult(item)

    }

    /*override fun onJokeClickListener(joke: String) {
           //Toast.makeText(requireContext(),"Cell clicked", Toast.LENGTH_SHORT).show()
           deleteJokeResult(joke)
    }*/

    private fun deleteJokeResult(item: String) {
        jokesViewModel.coroutineDeleteJokeResult(item)
    }

    private fun observeViews() {
        jokesViewModel._jokeId.observe(viewLifecycleOwner) { jokeId ->
            binding.textviewFirst.text = jokeId
        }

        chuckViewModel._chuckId.observe(viewLifecycleOwner) { chuckId ->
            binding.textviewFirst.text = chuckId
        }

        chuckViewModel.allChucks.observe(viewLifecycleOwner) { chucks ->
            // Data bind the recycler view
            binding.recyclerView.adapter = RecyclerChuckAdapter(chucks, this)
        }

        jokesViewModel.allJokes.observe(viewLifecycleOwner) { jokes ->
            // Data bind the recycler view
            binding.recyclerView.adapter = RecyclerJokeAdapter(jokes, this, this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firstFragmentJob?.cancel()
        firstFragmentJob = null
        _binding = null

    }




}