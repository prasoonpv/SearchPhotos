package com.prasoon.task.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prasoon.task.R
import com.prasoon.task.ui.main.model.PhotoResp
import kotlinx.android.synthetic.main.movie_items.view.*

class PhotoAdapter(
    private var searchList: ArrayList<PhotoResp>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    // Method #1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_items, parent, false)
        return ViewHolder(
            view,
            interaction
        )
    }

    // Method #2
    override fun getItemCount() = searchList.size

    // Method #3
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = searchList[position])
    }

    fun addAll(search: List<PhotoResp>?) {
        this.searchList.addAll(search!!)
        notifyDataSetChanged()
    }

    fun clearAll(){
        this.searchList = ArrayList()
        notifyDataSetChanged()
    }

    // Method #5
    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        // Method #6
        fun bind(item: PhotoResp) {
            itemView.tvTitle.text = item.title
            var imageUrl = "https://farm"+item.farm+".staticflickr.com/"+item.server+"/"+item.id+"_"+item.secret+"_m.jpg"
            Glide.with(itemView).load(imageUrl).placeholder(R.drawable.ic_launcher_background)
                .into(itemView.iv_image)
            //Handle item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition,item)
            }
        }

    }

    // Method #7
    interface Interaction {
        fun onItemSelected(position: Int, item: PhotoResp)
    }
}