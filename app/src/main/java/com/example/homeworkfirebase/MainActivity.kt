package com.example.homeworkfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SimpleAdapter
    private val products = mutableListOf<Product>()
    private lateinit var productsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SimpleAdapter(products) { product ->
            Toast.makeText(
                this,
                "${product.name} - $${product.price}",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.adapter = adapter

        productsRef = FirebaseDatabase
            .getInstance()
            .getReference("products")

        loadProducts()
    }

    private fun loadProducts() {
        productsRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()

                for (child in snapshot.children) {
                    val product = child.getValue(Product::class.java)

                    // Skip null list entries (index 0 in your DB)
                    if (product?.name != null && product.imageUrl != null) {
                        products.add(product)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to load products",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
