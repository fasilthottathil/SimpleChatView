package com.fasilthottathil.simplechatview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fasilthottathil.simplechatview.databinding.*
import com.fasilthottathil.simplechatview.model.ChatMessage
import com.fasilthottathil.simplechatview.util.SimpleChatUtil.Companion.getTimeAgo
import com.fasilthottathil.simplechatview.widget.SimpleChatView.Companion.TYPE_IMAGE
import com.fasilthottathil.simplechatview.widget.SimpleChatView.Companion.TYPE_TEXT
import com.fasilthottathil.simplechatview.widget.SimpleChatView.Companion.TYPE_VIDEO

class SimpleChatAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var chatMessage: ArrayList<ChatMessage> = arrayListOf()
    private var onChatImageClickListener: ((ChatMessage) -> Unit)? = null
    private var onChatVideoClickListener: ((ChatMessage) -> Unit)? = null
    private var onChatUserImageClickListener: ((ChatMessage) -> Unit)? = null
    private var onChatUsernameClickListener: ((ChatMessage) -> Unit)? = null
    private var onMessageClickListener:((ChatMessage)->Unit)? = null

    companion object {
        const val TYPE_TEXT_RIGHT = 0
        const val TYPE_TEXT_LEFT = 1
        const val TYPE_IMAGE_RIGHT = 2
        const val TYPE_IMAGE_LEFT = 3
        const val TYPE_VIDEO_RIGHT = 4
        const val TYPE_VIDEO_LEFT = 5
    }

    inner class SimpleChatDiffUtil(
        private val oldList: List<ChatMessage>,
        private val newList: List<ChatMessage>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT_RIGHT -> {
                TypeTextSend(
                    SendTextMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_TEXT_LEFT -> {
                TypeTextReceive(
                    ReceiveTextMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_IMAGE_RIGHT -> {
                TypeImageSend(
                    SendImageMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_IMAGE_LEFT -> {
                TypeImageReceive(
                    ReceiveImageMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_VIDEO_RIGHT -> {
                TypeVideoSend(
                    SendVideoMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                TypeVideoReceive(
                    ReceiveVideoMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(chatMessage[position].view_type){
            TYPE_TEXT_RIGHT->(holder as TypeTextSend).bind(position)
            TYPE_TEXT_LEFT->(holder as TypeTextReceive).bind(position)
            TYPE_IMAGE_RIGHT->(holder as TypeImageSend).bind(position)
            TYPE_IMAGE_LEFT->(holder as TypeImageReceive).bind(position)
            TYPE_VIDEO_RIGHT->(holder as TypeVideoSend).bind(position)
            TYPE_VIDEO_LEFT->(holder as TypeVideoReceive).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return chatMessage.size
    }

    override fun getItemViewType(position: Int): Int {
        val isFromMe = chatMessage[position].is_from_me
        val viewType =  when (chatMessage[position].message_type) {
            TYPE_TEXT -> {
                if (isFromMe)
                    0
                else
                    1
            }
            TYPE_IMAGE -> {
                if (isFromMe)
                    2
                else
                    3
            }
            TYPE_VIDEO -> {
                if (isFromMe)
                    4
                else
                    5
            }
            else -> throw RuntimeException("MessageType in ChatMessage() is not added")
        }
        chatMessage[position].view_type = viewType
        return viewType
    }

    inner class TypeTextSend(private val binding: SendTextMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                txtMessage.text = chatMessage[position].message

                txtDate.text = getTimeAgo(chatMessage[position].timestamp)

                txtMessage.setOnClickListener {
                    onMessageClickListener?.invoke(chatMessage[position])
                }

            }
        }
    }

    inner class TypeTextReceive(private val binding: ReceiveTextMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                txtMessage.text = chatMessage[position].message

                txtUsername.text = chatMessage[position].username

                txtDate.text = getTimeAgo(chatMessage[position].timestamp)

                Glide.with(context)
                    .load(chatMessage[position].profile_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgProfile)

                txtMessage.setOnClickListener {
                    onMessageClickListener?.invoke(chatMessage[position])
                }

            }
        }
    }

    inner class TypeImageSend(private val binding: SendImageMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                txtDate.text = getTimeAgo(chatMessage[position].timestamp)

                Glide.with(context)
                    .load(chatMessage[position].message)
                    .into(imgMessage)

                imgMessage.setOnClickListener {
                    onChatImageClickListener?.invoke(chatMessage[position])
                }
            }
        }
    }

    inner class TypeImageReceive(private val binding: ReceiveImageMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                txtUsername.text = chatMessage[position].username

                txtDate.text = getTimeAgo(chatMessage[position].timestamp)

                Glide.with(context)
                    .load(chatMessage[position].profile_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgProfile)

                Glide.with(context)
                    .load(chatMessage[position].message)
                    .into(imgMessage)

                imgMessage.setOnClickListener {
                    onChatImageClickListener?.invoke(chatMessage[position])
                }

                imgProfile.setOnClickListener {
                    onChatUserImageClickListener?.invoke(chatMessage[position])
                }

                txtUsername.setOnClickListener {
                    onChatUsernameClickListener?.invoke(chatMessage[position])
                }
            }
        }
    }

    inner class TypeVideoSend(private val binding: SendVideoMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                txtDate.text = getTimeAgo(chatMessage[position].timestamp)

                Glide.with(context)
                    .load(chatMessage[position].message)
                    .into(imgMessage)


                imgMessage.setOnClickListener {
                    onChatImageClickListener?.invoke(chatMessage[position])
                }
            }
        }
    }

    inner class TypeVideoReceive(private val binding: ReceiveVideoMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                txtUsername.text = chatMessage[position].username
                txtDate.text = getTimeAgo(chatMessage[position].timestamp)

                Glide.with(context)
                    .load(chatMessage[position].profile_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgProfile)

                Glide.with(context)
                    .load(chatMessage[position].message)
                    .into(imgMessage)

                imgMessage.setOnClickListener {
                    onChatImageClickListener?.invoke(chatMessage[position])
                }

                imgProfile.setOnClickListener {
                    onChatUserImageClickListener?.invoke(chatMessage[position])
                }

                txtUsername.setOnClickListener {
                    onChatUsernameClickListener?.invoke(chatMessage[position])
                }
            }
        }
    }

    fun setOnChatImageClickListener(listener: (ChatMessage) -> Unit) {
        onChatImageClickListener = listener
    }

    fun setOnChatVideoClickListener(listener: (ChatMessage) -> Unit) {
        onChatVideoClickListener = listener
    }

    fun setOnChatUserImageClickListener(listener: (ChatMessage) -> Unit) {
        onChatUserImageClickListener = listener
    }

    fun setOnChatUsernameClickListener(listener: (ChatMessage) -> Unit) {
        onChatUsernameClickListener = listener
    }

    fun setOnMessageClickListener(listener: (ChatMessage) -> Unit) {
        onMessageClickListener = listener
    }

    fun addData(newList: List<ChatMessage>){
        val diffResult = DiffUtil.calculateDiff(SimpleChatDiffUtil(chatMessage,newList))
        chatMessage.addAll(chatMessage.union(newList))
        diffResult.dispatchUpdatesTo(this)
    }

    fun addData(chatMessage: ChatMessage){
        this.chatMessage.add(chatMessage)
        notifyItemInserted(this.chatMessage.size-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(chatMessage: ChatMessage){
        this.chatMessage.remove(chatMessage)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(position: Int){
        chatMessage.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearMessages(){
        chatMessage.clear()
        notifyDataSetChanged()
    }

}