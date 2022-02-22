package com.example.earthquake_app.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquake_app.Earthquake
import com.example.earthquake_app.databinding.EqListItemBinding

class EqAdapter : ListAdapter<Earthquake, EqAdapter.EqViewHolder> (DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback <Earthquake> (){

        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {

            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Earthquake) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqViewHolder {

        val binder = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return EqViewHolder(binder)
    }


    override fun onBindViewHolder(holder: EqViewHolder, position: Int) {

        val earthquake = getItem(position)

        holder.bind(earthquake)
    }

    inner class EqViewHolder(private val binder: EqListItemBinding) : RecyclerView.ViewHolder(binder.root){


        fun bind (earthquake: Earthquake){

            binder.magnitudeText.text = earthquake.magnitude.toString()
            binder.placeText.text = earthquake.place
            binder.executePendingBindings()
            binder.root.setOnClickListener{
                onItemClickListener(earthquake)
            }


        }

    }

}