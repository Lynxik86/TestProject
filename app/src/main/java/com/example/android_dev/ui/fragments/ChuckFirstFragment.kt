package com.example.android_dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
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
import com.example.android_dev.databinding.ChuckFragmentFirstBinding
import com.example.android_dev.ui.recyclerview.ButtonClickListener
import com.example.android_dev.ui.recyclerview.ItemClickListener
import com.example.android_dev.ui.recyclerview.RecyclerChuckAdapter
import com.example.android_dev.viewmodel.ChuckViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ChuckFirstFragment : Fragment(),/* CoroutineScope,*/ ButtonClickListener, ItemClickListener {
    ///////////////////////////////////////////////////////////////////////
    //CoroutineScope
    /* private var chuckFirstFragmentJob: Job? = null
         get() {
             if (field == null) {
                 field = Job()
                 field!!.start()
             }
             return field
         }

     override val coroutineContext: CoroutineContext
         get() = CoroutinesDispatchers.Default + chuckFirstFragmentJob!!*/

    private var _binding: ChuckFragmentFirstBinding? = null

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

    lateinit var recyclerChuckAdapter: RecyclerChuckAdapter

    //вызывается в момент создания View и в нём инится сам layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChuckFragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Вызывается после того как layout проинициализирован.тут уже привязываем логику к вьюхам.
    // Это гарантирует что вьюха создана, и никакие null поинтеры падать не будут.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chuckTextviewFirst.setContent {
            MdcTheme(context = requireContext()) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .padding(top = 8.dp) // adding some space to the label
                            .background(color = LightGray)
                    ) {
                        ChuckObserveUpperView()
                        ChuckDropDownMenu()
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .padding(top = 15.dp) // adding some space to the label

                    ) {


                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween


                    ) {
                       // Column {
                            ChuckButtonAddChuck()
                            ChuckButtonAddJokes()
                            Spacer(Modifier.width(50.0.dp))
                      //  }
                    }

                }
            }
        }


        // Get the view model
        /* viewModelChuck = ViewModelProvider(this)[ChuckViewModel::class.java]
         jokesViewModel = ViewModelProvider(this)[JokeViewModel::class.java]*/
        //////////////////////////////////////////////////////////////////////
        /* val popupMenu = PopupMenu(requireContext(), binding.textviewFirst)
         popupMenu.inflate(R.menu.pop_up_menu)*/
        /*binding.textviewFirst.setOnClickListener {
               popUpUpdate(popupMenu)
               popupMenu.show()
           }*/

        /////////////////////////////////////////////////////////////////////
        //RececlerView on Flow
        /*  setupRecyclerView()
        observeChuck()*/


        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        /* binding.chuckButtonAddChuck.setOnClickListener {
             chuckViewModel.coroutineGetChuck()
         }
         binding.chuckButtonAddJokes.setOnClickListener {
             findNavController().navigate(R.id.action_ChuckFirstFragment_to_JokeFirstFragment)
         }
 */
    }

    @Preview
    @Composable
    private fun ChuckDropDownMenu() {
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier.width(50.dp)) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Show Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = { deleteDBChuck() }) {
                    Text(text = "Clear All History")

                }
            }

        }

    }


    @Preview
    @Composable
    private fun ChuckObserveUpperView() {
        val viewModel: ChuckViewModel = viewModel()
        val chuckId: String by viewModel._chuckId.collectAsState()
        Text(
            text = chuckId,
            fontSize = 16.sp,
            color = Color(129, 144, 219),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.width(350.dp),
            //fontStyle = FontStyle.Italic,
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

    @Composable
    fun ChuckButtonAddChuck() {
        OutlinedButton(


            onClick = {
                chuckViewModel.coroutineGetChuck()

            }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Gray
            )
        )

        {
            Text("Chuck Norris facts")
        }
    }

    @Composable
    fun ChuckButtonAddJokes() {

        OutlinedButton(
            onClick = { findNavController().navigate(R.id.action_ChuckFirstFragment_to_JokeFirstFragment) },
            modifier = Modifier.size(50.dp),  //avoid the oval shape

            // закругленные углы
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.elevation(),

            border = BorderStroke(1.dp, Color.Blue),
            contentPadding = PaddingValues(3.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
        )

        {
            Text("Dad jokes")
        }
    }

    private fun deleteDBChuck() {
        chuckViewModel.coroutineDeleteChuck()
    }

 /*   private fun setupRecyclerView() {
        // Specify layout for recycler view
        *//*     val linearLayoutManager = LinearLayoutManager(
                 requireContext(), RecyclerView.VERTICAL, false
             )
             binding.recyclerView.layoutManager = linearLayoutManager*//*

        binding.chuckRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }*/

    override fun onButtonClickListener(item: String) {
        deleteChuckResult(item)
    }

    override fun onItemClickListener(item: String) {

        val action = ChuckFirstFragmentDirections.actionChuckFirstFragmentToItemFragment(item)
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

    private fun observeChuck() {
        /* lifecycleScope.launchWhenCreated {
             chuckViewModel._chuckId.collect { chuckId ->
                 binding.textviewFirst.text = chuckId
             }
         }*/

        chuckViewModel.allChucks.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                recyclerChuckAdapter = RecyclerChuckAdapter(it, this, this)
                binding.chuckRecyclerView.adapter = recyclerChuckAdapter
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
        /* chuckFirstFragmentJob?.cancel()
         chuckFirstFragmentJob = null*/
        _binding = null
    }
}


