package com.example.esemkastore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.RecyclerListener
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.R

class CartAdapter(

): RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
  class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
    return MyViewHolder(view)
  }

  override fun getItemCount(): Int {
    TODO("Not yet implemented")
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    TODO("Not yet implemented")
  }

}