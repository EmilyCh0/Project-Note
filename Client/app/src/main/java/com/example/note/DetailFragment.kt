package com.example.note

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.note.data.Note
import com.example.note.databinding.FragmentDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var retrofitService: RetrofitService
    private lateinit var id: String
    private lateinit var note: Note
    private lateinit var mmenu: Menu
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
        this.mmenu = menu

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_btn -> {
                CoroutineScope(Dispatchers.IO).launch {
                    retrofitService.deleteNote(id)
                }
                findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
                Toast.makeText(context, getString(R.string.delete_complete), Toast.LENGTH_SHORT).show()
            }
            R.id.save_btn -> {
                CoroutineScope(Dispatchers.IO).launch {
                    note.title = binding.titleEt.text.toString()
                    note.content = binding.contentEt.text.toString()
                    retrofitService.updateNote(id = id, note = note)
                }
                findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
                Toast.makeText(context, getString(R.string.save_complete), Toast.LENGTH_SHORT).show()
            }
            else -> { // fav
                CoroutineScope(Dispatchers.Main).launch {
                    note.fav = !note.fav
                    retrofitService.updateNote(id = id, note = note)
                    if(note.fav) {
                        mmenu.getItem(0).setIcon(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_baseline_star_24) })
                        //Toast.makeText(context, "즐겨찾기 등록", Toast.LENGTH_SHORT).show()
                    }else{
                        mmenu.getItem(0).setIcon(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_baseline_star_outline_24) })
                        //Toast.makeText(context, "즐겨찾기 해제", Toast.LENGTH_SHORT).show()
                    }
                }
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

        CoroutineScope(Dispatchers.Main).launch {
            val list = retrofitService.getOneNote(id)
            note = list[0]
            binding.titleEt.setText(note.title)
            binding.contentEt.setText(note.content)

            if(note.fav) {
                mmenu.getItem(0).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24))
            }

            (activity as AppCompatActivity?)!!.supportActionBar!!.title = note.timestamp?.substring(0,10)
        }

    }

}