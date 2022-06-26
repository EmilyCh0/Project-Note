package com.example.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.data.Note
import com.example.note.databinding.NoteViewBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NoteAdapter: PagingDataAdapter<Note, NoteAdapter.NoteViewHolder>(NoteComparator) {

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
            editIv.setOnClickListener {

            }
            deleteIv.setOnClickListener {
                // are you sure?
                // delete
                this@NoteAdapter.notifyDataSetChanged()
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

}