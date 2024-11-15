package com.anshuman.uniroomie.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Adapter.UserAdapter
import com.anshuman.uniroomie.Modles.User


import com.anshuman.uniroomie.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<User>()
    private lateinit var searchbar:EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout and initialize ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        searchbar = binding.searchEdt
        // Set up RecyclerView

        // Reference RecyclerView from binding
        recommendedRecyclerView = binding.recommendedView
        recommendedRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Set up UserAdapter and attach it to RecyclerView
        userAdapter = UserAdapter(userList)
        recommendedRecyclerView.adapter = userAdapter

        // Fetch data from Firebase
        fetchDataFromFirebase()


        // SearchBar Filter Function where you need to fatch the data
        searchbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    private fun fetchDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()

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
                    Log.e("HomeFragment", "JSON Parsing error: ${e.message}")
                    Toast.makeText(requireContext(), "Data format error", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("HomeFragment", "Error: ${e.message}")
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("HomeFragment", "Database error: ${databaseError.message}")
                Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
