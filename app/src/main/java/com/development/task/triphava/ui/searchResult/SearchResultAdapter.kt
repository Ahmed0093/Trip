package com.development.task.triphava.ui.searchResult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.development.task.triphava.ImageLoader
import com.development.task.triphava.R
import com.development.task.triphava.model.Trips
import com.development.task.triphava.repository.COMPLETED_STATUS
import kotlinx.android.synthetic.main.row_trip_item.view.*

class SearchResultAdapter(
    private val mListener: OnItemClicked,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<SearchResultAdapter.Companion.ArticleViewHolder>() {

    companion object {

        class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val start_time: TextView = view.start_time
            val finalCost: TextView = view.final_cost
            val rating_bar: RatingBar = view.rating_bar
            val pickUpTxt: TextView = view.pickupText
            val dropOffText: TextView = view.dropOfText
            val statusText: TextView = view.statusText
            val image: ImageView = view.statusImage
            val arrowImage: ImageView = view.arrow_image
        }
    }

    private var resultsList: List<Trips> = ArrayList()

    fun setData(data: List<Trips>) {
        this.resultsList = data
        this.notifyDataSetChanged()
    }

    fun clearData() {
        resultsList = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultAdapter.Companion.ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_trip_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = resultsList[position]
        holder.start_time.text = item.request_date
        holder.finalCost.text = item.cost.toString() + item.cost_unit
        holder.dropOffText.text = item.dropoff_location
        holder.pickUpTxt.text = item.pickup_location
        holder.statusText.text = item.status
        holder.rating_bar.rating = item.driver_rating.toFloat()
        if(item.status == COMPLETED_STATUS) {
            imageLoader.providesGlide().load(R.drawable.ic_check_40dp).centerInside().into(holder.image)
        } else {
            imageLoader.providesGlide().load(R.drawable.ic_cancel_black_24dp).centerInside().into(holder.image)
        }
        holder.itemView.setOnClickListener { _ -> mListener.onClick(item) }

    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    interface OnItemClicked {
        fun onClick(trips: Trips)
    }


}