package com.example.note.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.R
import com.example.note.RetrofitService
import com.example.note.databinding.FragmentFavBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    lateinit var favViewModel: FavViewModel
    lateinit var favAdapter: FavAdapter
    private lateinit var retrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favFragment = this

        retrofitService = RetrofitService.getInstance()

        val factory = FavViewModelFactory(retrofitService)

        favViewModel = ViewModelProvider(this, factory).get(FavViewModel::class.java)
        favAdapter = FavAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            favViewModel.items.collectLatest { pagedDate ->
                favAdapter.submitData(pagedDate)
            }
        }
    }
}