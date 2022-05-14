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

        binding = FragmentMainBinding.inflate(layoutInflater)

        retrofitService = RetrofitService.getInstance()

        val opt = OptRequest()
        val factory = NoteViewModelFactory(retrofitService, opt)

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


}