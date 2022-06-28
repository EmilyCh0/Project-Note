package com.example.note.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.data.Note
import com.example.note.databinding.NoteViewBinding

class FavAdapter: PagingDataAdapter<Note, FavAdapter.FavViewHolder>(FavComparator) {

    inner class FavViewHolder(private val binding: NoteViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Note) = with(binding){
            val dateStr = item.timestamp
            dateTv.text = dateStr?.substring(0 until 10) ?: item.timestamp.toString()
            titleTv.text = item.title
            contentTv.text = item.content
            deleteIv.visibility = View.GONE
        }
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val item = getItem(position)
        item?.let{holder.bind(it)}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(NoteViewBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    object FavComparator: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}