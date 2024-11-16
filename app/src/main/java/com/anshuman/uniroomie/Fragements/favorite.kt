package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshuman.uniroomie.Adapter.FavoriteAdapter
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.FragmentFavoriteBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class favorite : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteList: MutableList<User>
    private lateinit var database: FirebaseDatabase
    private lateinit var favoritesRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentFavoriteBinding.inflate(inflater , container , false)

        binding.topAppBar.title = "Favorites"



        val FavRecycleView = binding.favRecycleView
        // Initialize Firebase references
        database = FirebaseDatabase.getInstance()
        favoritesRef = database.getReference("favorites")

        // Initialize the list and the adapter
        favoriteList = mutableListOf()
        favoriteAdapter = FavoriteAdapter(requireContext(), favoriteList)

        FavRecycleView.layoutManager = LinearLayoutManager(requireContext())
        FavRecycleView.setHasFixedSize(true)
        FavRecycleView.adapter = favoriteAdapter

     // Call the FavoriteData Fathch data For the New one
        fetchFavoriteData()

        return binding.root
    }
    private fun fetchFavoriteData() {
        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    favoriteList.clear()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        user?.let {
                            it.isFavorited = true // Mark as favorited
                            favoriteList.add(it)
                        }
                    }
                    favoriteAdapter.updateFavoriteList(favoriteList) // Update the adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error (e.g., display a message)
                Log.e("Firebase", "Error fetching favorite data: ${error.message}")
            }
        })
    }



}