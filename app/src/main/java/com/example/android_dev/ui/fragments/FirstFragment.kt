package com.example.android_dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_dev.R
import com.example.android_dev.databinding.FragmentFirstBinding
import com.example.android_dev.ui.recyclerview.ButtonClickListener
import com.example.android_dev.ui.recyclerview.ItemClickListener
import com.example.android_dev.ui.recyclerview.RecyclerChuckAdapter
import com.example.android_dev.ui.recyclerview.RecyclerJokeAdapter
import com.example.android_dev.viewmodel.ChuckViewModel
import com.example.android_dev.viewmodel.JokeViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    ///////////////////////////////////////////////////////
    //Получение обекта вью модели
    /* private lateinit var viewModelChuck: ChuckViewModel
     private lateinit var jokesViewModel: JokeViewModel*/

    ////////////////////////////////////////////////////////
    //Получение объекта  вью модели с использованием LiveData
    private val chuckViewModel: ChuckViewModel by viewModels()
    private val jokesViewModel: JokeViewModel by viewModels()

    lateinit var recyclerJokeAdapter: RecyclerJokeAdapter
    lateinit var recyclerChuckAdapter: RecyclerChuckAdapter

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

        binding.textviewFirst.setContent {

            MdcTheme(context = requireContext()) {
                ChuckObserveUpperView()
                JokeObserveUpperView()
            //    ResetAndClearTextField()
            }


        }


        // Get the view model
        /* viewModelChuck = ViewModelProvider(this)[ChuckViewModel::class.java]
         jokesViewModel = ViewModelProvider(this)[JokeViewModel::class.java]*/

        //////////////////////////////////////////////////////////////////////

        val popupMenu = PopupMenu(requireContext(), binding.textviewFirst)
        popupMenu.inflate(R.menu.pop_up_menu)

        setupRecyclerView()

        observeJoke()
        observeChuck()

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
        //////////////////////////////////////////////////////////////
        //ДО переноса в рамки архитектуры MVVM
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

    @Preview
    @Composable
    private fun ChuckObserveUpperView() {

        val viewModel: ChuckViewModel = viewModel()
        val chuckId: String by viewModel._chuckId.collectAsState()
        Text(
            text = chuckId,
            fontSize = 12.sp,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.width(150.dp)
        )


    /*    ClickableText(
            text = AnnotatedString("${chuckId}"),


        onClick = { offset ->
            val popupMenu = PopupMenu(requireContext(), binding.textviewFirst)
            popupMenu.inflate(R.menu.pop_up_menu)
            popUpUpdate(popupMenu)
            popupMenu.show()
        }

        )*/
    }

    @Preview
    @Composable
    private fun JokeObserveUpperView(){

        //val jokeId by remember { mutableStateOf("") }

        val viewModel: JokeViewModel = viewModel()
        val jokeId: String by viewModel._jokeId.collectAsState()
        //val jokeId by viewModel._jokeId.observeAsState("")
        Text(text = jokeId, fontSize = 12.sp,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.width(150.dp))
        // ResetAndClearTextField()
    }



/*    @Composable
    private fun ResetAndClearTextField() {

        var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = textFieldValue,
            onValueChange = { newText ->
                textFieldValue = newText}
        )
    }*/




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
        /*     val linearLayoutManager = LinearLayoutManager(
                 requireContext(), RecyclerView.VERTICAL, false
             )
             binding.recyclerView.layoutManager = linearLayoutManager*/

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onButtonClickListener(item: String) {
        //Toast.makeText(requireContext(),"Cell clicked", Toast.LENGTH_SHORT).show()
        deleteJokeResult(item)
        deleteChuckResult(item)
    }

    override fun onItemClickListener(item: String) {

        val action = FirstFragmentDirections.actionFirstFragmentToItemFragment(item)

        findNavController().navigate(directions = action)
        // findNavController().navigate(R.id.action_FirstFragment_to_ItemFragment)
        /*val fr = parentFragmentManager.beginTransaction()
        fr.replace(R.id.fr1, ItemFragment())
        fr.addToBackStack(null)
        fr.commit()*/
    }

    private fun deleteChuckResult(item: String) {
        chuckViewModel.coroutineDeleteChuckResult(item)
    }

    private fun deleteJokeResult(item: String) {
        jokesViewModel.coroutineDeleteJokeResult(item)
    }

    private fun observeJoke() {




        /* lifecycleScope.launchWhenCreated {
             jokesViewModel._jokeId.collect { jokeId ->
                 binding.textviewFirst.text = jokeId
             }
         }*/

        jokesViewModel.allJokes.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                //  binding.recyclerView.adapter = RecyclerJokeAdapter(it, this, this)
                recyclerJokeAdapter = RecyclerJokeAdapter(it, this, this)
                binding.recyclerView.adapter = recyclerJokeAdapter
                recyclerJokeAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)
    }

    /* jokesViewModel._jokeId.observe(viewLifecycleOwner) { jokeId ->
          binding.textviewFirst.text = jokeId
      }
        jokesViewModel.allJokes.observe(viewLifecycleOwner) { jokes ->
            // Data bind the recycler view
            binding.recyclerView.adapter = RecyclerJokeAdapter(jokes, this, this)
    }*/

    private fun observeChuck() {

            /* lifecycleScope.launchWhenCreated {
                 chuckViewModel._chuckId.collect { chuckId ->
                     binding.textviewFirst.text = chuckId


                 }
             }*/

        chuckViewModel.allChucks.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                recyclerChuckAdapter = RecyclerChuckAdapter(it, this, this)
                binding.recyclerView.adapter = recyclerChuckAdapter
                recyclerChuckAdapter.submitList(it)
                /* binding.recyclerView.adapter = RecyclerChuckAdapter(it, this, this)*/

            }
            .launchIn(lifecycleScope)

        /*chuckViewModel._chuckId.observe(viewLifecycleOwner) { chuckId ->
            binding.textviewFirst.text = chuckId
        }

        chuckViewModel.allChucks.observe(viewLifecycleOwner) { chucks ->
            // Data bind the recycler view
            binding.recyclerView.adapter = RecyclerChuckAdapter(chucks, this, this)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firstFragmentJob?.cancel()
        firstFragmentJob = null
        _binding = null

    }

}


