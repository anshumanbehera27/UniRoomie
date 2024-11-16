package com.anshuman.uniroomie.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Adapter.UserMsgAdapter
import com.anshuman.uniroomie.Modles.User
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.Screens.MessageActivity
import com.anshuman.uniroomie.databinding.FragmentChartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chart : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private lateinit var chatRecyclerView: RecyclerView
    private val userList = mutableListOf<User>()
    private val userIds = mutableListOf<String>()
    private lateinit var userMsgAdapter: UserMsgAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater, container, false)

        chatRecyclerView = binding.chartrecyclerView
        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchUsersFromFirebase()

        return binding.root
    }

    private fun fetchUsersFromFirebase() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance().reference.child("users")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                userIds.clear()

                for (userSnapshot in snapshot.children) {
                    val userProfile = userSnapshot.getValue(User::class.java)
                    val userId = userSnapshot.key // Firebase key is the unique ID

                    // Exclude current user from the chat list
                    if (userProfile != null && userId != null && userId != currentUserId) {
                        userList.add(userProfile)
                        userIds.add(userId)
                    }
                }

                // Set up the adapter with click listener
                userMsgAdapter = UserMsgAdapter(userList) { position ->
                    navigateToMessageActivity(userIds[position], userList[position].personalDetails.userName)
                }
                chatRecyclerView.adapter = userMsgAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToMessageActivity(userId: String, userName: String) {
        val intent = Intent(requireContext(), MessageActivity::class.java)
        intent.putExtra("USER_ID", userId) // Pass the unique user ID
        intent.putExtra("USER_NAME", userName) // Pass the user name
        startActivity(intent)
    }
}
