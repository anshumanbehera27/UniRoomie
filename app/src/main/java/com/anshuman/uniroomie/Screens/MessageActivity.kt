package com.anshuman.uniroomie.Screens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Adapter.MessageAdapter
import com.anshuman.uniroomie.Modles.Message
import com.anshuman.uniroomie.R
import com.anshuman.uniroomie.databinding.ActivityMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMessageBinding
    private lateinit var senderId: String
    private lateinit var receiverId: String
    private lateinit var receiverName: String
    private lateinit var database: DatabaseReference
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private val messageList = mutableListOf<Message>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        senderId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        receiverId = intent.getStringExtra("USER_ID") ?: ""
        receiverName = intent.getStringExtra("USER_NAME") ?: "Unknown"

        binding.name.text = receiverName

        binding.back.setOnClickListener{
            onBackPressed()

        }


        database = FirebaseDatabase.getInstance().reference

        // Initialize RecyclerView
        messagesRecyclerView = binding.chatSection
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)



        // Pass senderId to the adapter to handle message alignment
        messageAdapter = MessageAdapter(messageList, senderId)
        messagesRecyclerView.adapter = messageAdapter


        loadMessages()

        // Handle send button
        binding.send.setOnClickListener {
            sendMessage()
        }
    }

    private fun loadMessages() {
        val chatId = if (senderId > receiverId) "$senderId-$receiverId" else "$receiverId-$senderId"
        database.child("chats").child(chatId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MessageActivity, "Failed to load messages", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendMessage() {
        val messageText = binding.msgEditText.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val chatId = if (senderId > receiverId) "$senderId-$receiverId" else "$receiverId-$senderId"
            val messageId = database.child("chats").child(chatId).push().key!!

            val message = Message(
                messageId = messageId,
                senderId = senderId,
                receiverId = receiverId,
                senderName = "You", // Replace with actual sender name
                receiverName = receiverName,
                message = messageText,
                timestamp = System.currentTimeMillis()
            )

            database.child("chats").child(chatId).child(messageId).setValue(message)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.msgEditText.text.clear()
                    } else {
                        Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
