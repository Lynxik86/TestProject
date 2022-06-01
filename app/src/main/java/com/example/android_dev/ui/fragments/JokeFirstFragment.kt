package com.example.android_dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.example.android_dev.databinding.JokeFragmentFirstBinding
import com.example.android_dev.ui.recyclerview.ButtonClickListener
import com.example.android_dev.ui.recyclerview.ItemClickListener
import com.example.android_dev.ui.recyclerview.RecyclerJokeAdapter
import com.example.android_dev.viewmodel.JokeViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class JokeFirstFragment : Fragment(), /*CoroutineScope,*/ ButtonClickListener, ItemClickListener {
    /* private var jokeFirstFragmentJob: Job? = null
         get() {
             if (field == null) {
                 field = Job()
                 field!!.start()
             }
             return field
         }

     override val coroutineContext: CoroutineContext
         get() = Dispatchers.Default + jokeFirstFragmentJob!!*/

    private var _binding: JokeFragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    ///////////////////////////////////////////////////////
    //Получение обекта вью модели
    /* private lateinit var viewModelChuck: ChuckViewModel
     private lateinit var jokesViewModel: JokeViewModel*/

    ////////////////////////////////////////////////////////
    //Получение объекта  вью модели с использованием LiveData
    private val jokesViewModel: JokeViewModel by viewModels()

    lateinit var recyclerJokeAdapter: RecyclerJokeAdapter

    //вызывается в момент создания View и в нём инится сам layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = JokeFragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    //вызывается после того как layout проинициализирован.тут уже привязываем логику к вьюхам.
    // Это гарантирует что вьюха создана, и никакие null поинтеры падать не будут.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.jokeTextviewFirst.setContent {
            MdcTheme(context = requireContext()) {

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(top = 8.dp) // adding some space to the label
                        .background(color = Color.LightGray)
                ) {
                    JokeObserveUpperView()
                    JokeDropDownMenu()
                }

            }
        }


        // Get the view model
        /* viewModelChuck = ViewModelProvider(this)[ChuckViewModel::class.java]
         jokesViewModel = ViewModelProvider(this)[JokeViewModel::class.java]*/

        //////////////////////////////////////////////////////////////////////

        /*   val popupMenu = PopupMenu(requireContext(), binding.textviewFirst)
           popupMenu.inflate(R.menu.pop_up_menu)*/

        setupRecyclerView()

        observeJoke()

        /*   binding.textviewFirst.setOnClickListener {
               popUpUpdate(popupMenu)
               popupMenu.show()
           }*/
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        binding.jokeButtonAddJokes.setOnClickListener {
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
        binding.jokeButtonAddChuck.setOnClickListener {
            findNavController().navigate(R.id.action_JokeFirstFragment_to_ChuckFirstFragment)
        }
    }

    @Preview
    @Composable
    private fun JokeDropDownMenu() {
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier.width(50.dp)) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Show Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = { deleteDBJoke() }) {
                    Text(text = "Clear All History")

                }
            }

        }

    }


    @Preview
    @Composable
    private fun JokeObserveUpperView() {

        val viewModel: JokeViewModel = viewModel()
        val jokeId: String by viewModel._jokeId.collectAsState()
        //val jokeId by viewModel._jokeId.observeAsState("")
        Text(
            text = jokeId, fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.width(350.dp),
            fontWeight = FontWeight.Bold,
        )


    }

    /*   private fun popUpUpdate(popupMenu: PopupMenu) {

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
       }*/


    private fun deleteDBJoke() {
        jokesViewModel.coroutineDeleteJokes()
    }

    private fun setupRecyclerView() {
        // Specify layout for recycler view
        /*     val linearLayoutManager = LinearLayoutManager(
                 requireContext(), RecyclerView.VERTICAL, false
             )
             binding.recyclerView.layoutManager = linearLayoutManager*/

        binding.jokeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onButtonClickListener(item: String) {
        //Toast.makeText(requireContext(),"Cell clicked", Toast.LENGTH_SHORT).show()
        deleteJokeResult(item)
    }

    override fun onItemClickListener(item: String) {
        val action = JokeFirstFragmentDirections.actionJokeFirstFragmentToItemFragment(item)
        findNavController().navigate(directions = action)
        // findNavController().navigate(R.id.action_FirstFragment_to_ItemFragment)
        /*val fr = parentFragmentManager.beginTransaction()
        fr.replace(R.id.fr1, ItemFragment())
        fr.addToBackStack(null)
        fr.commit()*/
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
                binding.jokeRecyclerView.adapter = recyclerJokeAdapter
                recyclerJokeAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)
    }
    ////////////////////////////////////////////////////////////////////
    //Observe Live Data
    /* jokesViewModel._jokeId.observe(viewLifecycleOwner) { jokeId ->
          binding.textviewFirst.text = jokeId
      }
        jokesViewModel.allJokes.observe(viewLifecycleOwner) { jokes ->
            // Data bind the recycler view
            binding.recyclerView.adapter = RecyclerJokeAdapter(jokes, this, this)
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        /*  jokeFirstFragmentJob?.cancel()
          jokeFirstFragmentJob = null*/
        _binding = null
    }
}