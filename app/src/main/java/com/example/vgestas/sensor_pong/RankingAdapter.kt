package com.example.vgestas.sensor_pong

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_ranking.view.*


class RankingAdapter (private val myDataset: MutableList<Score> = mutableListOf()) :  RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    var rankingUser:Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_ranking, parent, false))
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.usernameRanking.text = myDataset[position].username
        holder.itemView.scoreRanking.text = myDataset[position].score.toString()
        holder.itemView.dateRanking.text = myDataset[position].date
        holder.itemView.rankingPlace.text = (position+1).toString()+"."

        if(position == rankingUser - 1)
        {
            holder.itemView.layoutRanking.setBackgroundColor(Color.parseColor("#f39500"))
            holder.itemView.usernameRanking.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.scoreRanking.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.dateRanking.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.rankingPlace.setTextColor(Color.parseColor("#FFFFFF"))
        }
        else
        {
            holder.itemView.layoutRanking.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.itemView.usernameRanking.setTextColor(Color.parseColor("#000000"))
            holder.itemView.scoreRanking.setTextColor(Color.parseColor("#000000"))
            holder.itemView.dateRanking.setTextColor(Color.parseColor("#000000"))
            holder.itemView.rankingPlace.setTextColor(Color.parseColor("#000000"))
        }
    }

    fun setScore(listScore:List<Score>){

        myDataset.clear()
        myDataset.addAll(listScore)

        notifyDataSetChanged()
    }

    fun setRanking(rankingUserParty : Int)
    {
        rankingUser = rankingUserParty
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}