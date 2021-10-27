package com.example.headsupapproom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*

class RecycelerAdapter (val activity: HeadsActivity, val CelebList:List<CelebTable>) : RecyclerView.Adapter<RecycelerAdapter.itemViewHolder>() {
    class itemViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_view, parent,false
        ))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val CelebObj=CelebList[position]
        val name=CelebList[position].name
        val Taboo1=CelebList[position].t1
        val Taboo2=CelebList[position].t2
        val Taboo3=CelebList[position].t3

        holder.itemView.apply {
            tvName.text=name
            tvT1.text=Taboo1
            tvT2.text=Taboo2
            tvT3.text=Taboo3
        }
    }

    override fun getItemCount(): Int = CelebList.size

}