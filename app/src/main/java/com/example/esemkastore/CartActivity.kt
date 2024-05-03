package com.example.esemkastore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.adapter.CartAdapter
import com.example.esemkastore.model.CartResponse
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.internal.notify

class CartActivity : AppCompatActivity() {
  private lateinit var tabLayout: TabLayout
  private lateinit var cartAdapter: CartAdapter
  private lateinit var rvCart : RecyclerView
  private var cartItem: ArrayList<CartResponse> = arrayListOf()
  private lateinit var name: String
  private lateinit var count: String
  private lateinit var price: String
  private lateinit var itemId: String
  private lateinit var sharedPreferences: SharedPreferences

  private fun initComponents() {
    tabLayout = findViewById(R.id.tb_home)
    rvCart = findViewById(R.id.rv_cart)

    Log.i("item", name)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cart)
    supportActionBar!!.title = "Cart"
    supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E67E23")))
    name = intent.getStringExtra("name") ?: ""
    count = intent.getStringExtra("count")?: ""
    price = intent.getStringExtra("price") ?: ""
    itemId = intent.getStringExtra("id") ?: ""
    sharedPreferences = getSharedPreferences("CartItems", Context.MODE_PRIVATE)
    initComponents()
    setupListener()
    setupList()
    loadCartItems()
    getCartItem()
    saveCartItems()
  }

  override fun onPause() {
    super.onPause()
    saveCartItems()
  }

  private fun saveCartItems() {
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(cartItem)
    editor.putString("cartItems", json)
    editor.apply()
  }

  private fun loadCartItems() {
    val gson = Gson()
    val json = sharedPreferences.getString("cartItems", null)
    val type = object: TypeToken<ArrayList<CartResponse>>() {}.type
    cartItem = gson.fromJson(json, type) ?: ArrayList()
    cartAdapter.setData(cartItem)
  }
  private fun setupList() {
    cartAdapter = CartAdapter(this@CartActivity, arrayListOf())  { position ->
      // Call function to delete item from cartItem list
      deleteCartItem(position)
      cartAdapter.setData(cartItem)
    }
    rvCart.adapter = cartAdapter
  }

  fun deleteCartItem(position: Int) {
    cartItem.removeAt(position)
    cartAdapter.notifyItemRemoved(position)
    saveCartItems()
  }

  private fun setupListener() {
    val tab = tabLayout.getTabAt(1)
    tab?.select()
    tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
      override fun onTabSelected(p0: TabLayout.Tab?) {
        val position: Int = p0!!.position
        if(position == 0) {
          replaceActivity(HomeActivity())
          Toast.makeText(this@CartActivity, "Home", Toast.LENGTH_SHORT).show()
        } else if(position == 1) {
          replaceActivity(CartActivity())
          Toast.makeText(this@CartActivity, "Cart", Toast.LENGTH_SHORT).show()
        } else if (position == 2) {
          Toast.makeText(this@CartActivity, "History", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onTabUnselected(p0: TabLayout.Tab?) {}

      override fun onTabReselected(p0: TabLayout.Tab?) {}

    })
  }

  private fun getCartItem () {
    if (itemId.isNotEmpty()) {
      val cartResponse = CartResponse(
        id = itemId.toInt(),
        name = name,
        count = count,
        price = price
      )
      cartItem.add(cartResponse)
      saveCartItems()
      cartAdapter.setData(cartItem)
      Log.i("item", cartItem.toString())
    } else {
      Log.e("getCartItem", "ItemId is empty")
    }
  }
  fun replaceActivity(activity: Activity) {
    val intent = Intent(this@CartActivity, activity::class.java)
    startActivity(intent)
  }
}