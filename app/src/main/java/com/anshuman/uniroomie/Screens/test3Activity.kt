package com.anshuman.uniroomie.Screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Adapter.UserAdapter
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.R
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class test3Activity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test3)

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Set up RecyclerView with LinearLayoutManager
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize UserAdapter with userList and set it to RecyclerView
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        // Fetch data from Firebase
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()

                // Parsing data from Firebase
                try {
                    val type = object : TypeToken<Map<String, Any>>() {}.type
                    val dataMap: Map<String, Any> = dataSnapshot.value as? Map<String, Any> ?: emptyMap()

                    for ((userId, userData) in dataMap) {
                        val jsonString = Gson().toJson(userData, type)
                        val user = Gson().fromJson(jsonString, User::class.java)?.copy(userId = userId)
                        user?.let { userList.add(it) }
                    }
                    userAdapter.notifyDataSetChanged()

                } catch (e: JsonSyntaxException) {
                    Log.e("test3Activity", "JSON Parsing error: ${e.message}")
                    Toast.makeText(this@test3Activity, "Data format error", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("test3Activity", "Error: ${e.message}")
                    Toast.makeText(this@test3Activity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("test3Activity", "Database error: ${databaseError.message}")
                Toast.makeText(this@test3Activity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
