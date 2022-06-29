package com.example.note

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.note.data.Note
import com.example.note.databinding.FragmentEditBinding
import kotlinx.coroutines.*
import java.util.*


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var retrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editFragment = this
        retrofitService = RetrofitService.getInstance()

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        binding.timestampTv.text = ""+year+"-"+month+"-"+day

        // 작성 날짜 선택 기능 - MongoDB utc time, local time 파싱 필요..
//        binding.timestampTv.setOnClickListener {
//            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
//                binding.timestampTv.setText(""+myear+"-"+mmonth+"-"+mdayOfMonth)
//            }, year, month, day)
//            datePickerDialog.show()
//        }

        binding.cancelBtn.setOnClickListener {
            goToMainFragment()
        }

        binding.saveBtn.setOnClickListener {
            val t: String = binding.titleEt.text.toString()
            val c: String = binding.contentEt.text.toString()
            val newNote = Note(title = t, content = c)
            CoroutineScope(Dispatchers.Main).launch {
                retrofitService.insertNote(newNote)
            }

            goToMainFragment()
        }

    }

    private fun goToMainFragment(){
        findNavController().navigate(R.id.action_editFragment_to_mainFragment)
    }
}