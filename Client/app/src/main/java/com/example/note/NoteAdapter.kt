package com.example.note

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.data.Mid
import com.example.note.data.Note
import com.example.note.databinding.NoteViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NoteAdapter: PagingDataAdapter<Note, NoteAdapter.NoteViewHolder>(NoteComparator) {

    val retrofitService = RetrofitService.getInstance()
    private lateinit var context: Context

    inner class NoteViewHolder(private val binding: NoteViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(item: Note) = with(binding){
            val dateStr = item.timestamp
//            val utcFormat : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA)
//            utcFormat.timeZone = TimeZone.getTimeZone("UTC")
//            val utcDate: Date = utcFormat.parse(dateStr)
//
//            val localFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.KOREA)
//            localFormat.timeZone = TimeZone.getDefault()
//
//            val localDate = localFormat.format(utcDate.date)

            dateTv.text = dateStr?.substring(0 until 10) ?: item.timestamp.toString()
            titleTv.text = item.title
            contentTv.text = item.content
            deleteIv.setOnClickListener {
                // dialog box
                val builder = AlertDialog.Builder(context)
                builder.setTitle("노트 삭제")
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("삭제",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            val id: String = item.id!!
                            deleteItem(id)
                        }
                        )
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener { dialogInterface, i ->  }
                        )
                builder.show()


            }
            container.setOnClickListener { view ->
                val arg = Mid(id = item.id!!)
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(mid = arg)
                view.findNavController().navigate(action)
            }
        }
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {holder.bindItem(it)}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        context = parent.context
        return NoteViewHolder(NoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    object NoteComparator: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            // objectID로 변경
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private fun deleteItem(id: String){
        CoroutineScope(Dispatchers.Main).launch {
            retrofitService.deleteNote(id)
            this@NoteAdapter.refresh()
        }
        //this@NoteAdapter.notifyDataSetChanged()
    }

}