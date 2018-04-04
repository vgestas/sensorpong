package com.example.vgestas.sensor_pong

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_ranking.view.*


class RankingAdapter (private val myDataset: MutableList<Score> = mutableListOf()) :  RecyclerView.Adapter<RankingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_ranking, parent, false))
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.usernameRanking.text = myDataset[position].username
        holder.itemView.scoreRanking.text = myDataset[position].score.toString()
        holder.itemView.dateRanking.text = myDataset[position].date
    }

    fun setScore(listScore:List<Score>){

        myDataset.clear()
        myDataset.addAll(listScore)

        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}