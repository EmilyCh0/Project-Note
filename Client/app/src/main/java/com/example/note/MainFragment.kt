package com.example.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.data.OptRequest
import com.example.note.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter
    private lateinit var retrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        binding = fragmentMainBinding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainFragment = this

        retrofitService = RetrofitService.getInstance()

        val factory = NoteViewModelFactory(retrofitService)

        noteViewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)
        noteAdapter = NoteAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            noteViewModel.items.collectLatest { pagedDate ->
                noteAdapter.submitData(pagedDate)
            }
        }

    }
}