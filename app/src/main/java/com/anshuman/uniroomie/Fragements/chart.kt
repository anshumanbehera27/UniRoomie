package com.anshuman.uniroomie.Fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Adapter.UserMsgAdapter
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.Modles.Usermsg
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.FragmentChartBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chart : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private lateinit var charRecyclerView: RecyclerView
    private val userList = mutableListOf<Usermsg>()
    private lateinit var usermsgAdapter: UserMsgAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)

        charRecyclerView = binding.chartrecyclerView
        charRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchUsersFromFirebase()

        return binding.root
    }

    private fun fetchUsersFromFirebase() {
        val database = FirebaseDatabase.getInstance().reference.child("users")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear() // Clear the list to avoid duplicates

                for (userSnapshot in snapshot.children) {
                    // Assuming 'UserProfile' is the parent model for each user in the Firebase database
                    val userProfile = userSnapshot.getValue(User::class.java)
                    if (userProfile != null) {
                        // Only fetch the username from PersonalDetails
                        val usermsg = Usermsg(
                            name = userProfile.personalDetails.userName // Fetch the username
                        )

                        userList.add(usermsg) // Add the username to the list
                    }
                }

                // Set up the adapter with the fetched user data
                usermsgAdapter = UserMsgAdapter(userList)
                charRecyclerView.adapter = usermsgAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
