package com.example.android_dev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_dev.databinding.FragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    //private val args: ItemFragmentArgs by navArgs<ItemFagmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeJoke()
    }

    private fun observeJoke() {

        /*  val item = args.item
          binding.jokeText.text = item*/

        val itemValue = requireArguments().getString("item")
        binding.jokeText.text = itemValue

        //  val itemValue = requireArguments().getString("item")
        //   binding.jokeText.text = requireArguments()
        /* Fragment2Args fragment2Args = Fragment2Args.fromBundle(getArguments())
         String arg3Value = fragment2Args.getArg3();*/

        /*val itemFragmentArgs = ItemFragmentArgs.fromBundle(arguments).myArgument
        val itemValue: String = itemFragmentArgs.getItem();*/
    }
}




