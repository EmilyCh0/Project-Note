package com.example.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.note.databinding.FragmentDetailBinding
import com.example.note.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var retrofitService: RetrofitService
    private lateinit var id: String
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_btn -> {
                CoroutineScope(Dispatchers.Main).launch {
                    retrofitService.deleteNote(id)
                }
                findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
            }
            R.id.save_btn -> {
                CoroutineScope(Dispatchers.Main).launch {
                    //retrofitService.deleteNote(id)
                }
            }
            else -> { // fav

            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailFragment = this

        retrofitService = RetrofitService.getInstance()

        id = args.mid.id


    }
}