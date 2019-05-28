package com.akondi.booksnippetskotlin.modules.car.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akondi.booksnippetskotlin.R
import com.akondi.booksnippetskotlin.listeners.OnCarItemClickListener
import com.akondi.booksnippetskotlin.mvp.model.Car
import com.akondi.booksnippetskotlin.utils.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.list_item_layout.view.*
import java.util.ArrayList

class CarAdapter(private val layoutInflater: LayoutInflater, private val listener: OnCarItemClickListener)
    : RecyclerView.Adapter<CarAdapter.Holder>(){

    override fun getItemCount(): Int {
        return carList.size
    }

    private val carList: MutableList<Car> = ArrayList()

    fun addCars(cars: List<Car>) {
        carList.clear()
        carList.addAll(cars)
        notifyDataSetChanged()
    }

    override
    fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view = layoutInflater.inflate(R.layout.list_item_layout, viewGroup, false)
        return Holder(view)
    }

    override
    fun onBindViewHolder(holder: Holder, i: Int) {
        holder.bind(carList[i])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mCarIcon: SimpleDraweeView
        private var mCarTitle: TextView
        private var mCarDescription: TextView

        init {
            mCarIcon = itemView.car_icon
            mCarTitle = itemView.textview_title
            mCarDescription = itemView.textview_preview_description
        }

        fun bind(car: Car) {
            mCarTitle.text = car.name
            mCarDescription.text = car.vin
            mCarIcon.controller = ImageUtil().reduceImageSize(
                "https://www.enterprise.com/content/dam/global-vehicle-images/cars/TOYO_CAMR_2018.png",
                200
            )
            mCarIcon.setOnClickListener({
                listener.onCarClicked()
            })
        }
    }
}
