package com.example.esemkastore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.RecyclerListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.R
import com.example.esemkastore.model.CartResponse

class CartAdapter(
  var items: ArrayList<CartResponse>
): RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
  class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    val ivCart = view.findViewById<ImageView>(R.id.iv_cart)
    val tvName = view.findViewById<TextView>(R.id.tv_cart_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_cart_count)
    val tvPrice = view.findViewById<TextView>(R.id.tv_cart_price)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
    return MyViewHolder(view)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val results = items[position]
    holder.tvName.text = results.name
    holder.tvPrice.text = results.price
    holder.tvCount.text = results.count
  }

  public fun setData(result: List<CartResponse>) {
    items.clear()
    items.addAll(result)
    notifyDataSetChanged()
  }

}