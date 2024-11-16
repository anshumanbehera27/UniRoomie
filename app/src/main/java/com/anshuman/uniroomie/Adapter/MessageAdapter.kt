package com.anshuman.uniroomie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.uniroomie.Modles.Message
import com.anshuman.uniroomie.R

class MessageAdapter(
    private val messageList: List<Message>,
    private val senderId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_SENT = 1
    private val ITEM_RECEIVED_LEFT = 2
    private val ITEM_RECEIVED_RIGHT = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_SENT -> SentMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_self, parent, false)
            )
            ITEM_RECEIVED_LEFT -> ReceivedMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_other, parent, false)
            )
            ITEM_RECEIVED_RIGHT -> ReceivedMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_other, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return when {
            message.senderId == senderId -> ITEM_SENT
            position % 2 == 0 -> ITEM_RECEIVED_LEFT
            else -> ITEM_RECEIVED_RIGHT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messageList.size

    // ViewHolder for sent messages
    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sentMessageText: TextView = itemView.findViewById(R.id.sentMessageText)
        private val senderNameText: TextView = itemView.findViewById(R.id.senderNameText)

        fun bind(message: Message) {
            sentMessageText.text = message.message
            senderNameText.text = message.senderName
        }
    }

    // ViewHolder for received messages
    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val receivedMessageText: TextView = itemView.findViewById(R.id.receivedMessageText)
        private val receiverNameText: TextView = itemView.findViewById(R.id.receiverNameText)

        fun bind(message: Message) {
            receivedMessageText.text = message.message
            receiverNameText.text = message.receiverName
        }
    }
}
