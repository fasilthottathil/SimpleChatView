package com.fasilthottathil.simplechatview.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasilthottathil.simplechatview.R
import com.fasilthottathil.simplechatview.adapter.SimpleChatAdapter
import com.fasilthottathil.simplechatview.databinding.SimpleChatViewWidgetBinding
import com.fasilthottathil.simplechatview.model.ChatMessage


class SimpleChatView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet, 0) {

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_IMAGE = 1
        const val TYPE_VIDEO = 2
    }


    private var _binding: SimpleChatViewWidgetBinding? = null
    private val binding get() = _binding!!
    private var isMoreLayoutVisible = false
    private val simpleChatAdapter by lazy { SimpleChatAdapter(context) }
    private var onChatImageClickListener: ((ChatMessage) -> Unit)? = null
    private var onChatVideoClickListener: ((ChatMessage) -> Unit)? = null
    private var onChatUserImageClickListener: ((ChatMessage) -> Unit)? = null
    private var onChatUsernameClickListener: ((ChatMessage) -> Unit)? = null
    private var onSelectImageClickListener: (() -> Unit)? = null
    private var onSelectVideoClickListener: (() -> Unit)? = null
    private var onCameraClickListener: (() -> Unit)? = null
    private var onMessageSendListener: ((String) -> Unit)? = null
    private var onMessageClickListener: ((ChatMessage) -> Unit)? = null

    init {

        val layoutInflater = LayoutInflater.from(context)

        _binding = SimpleChatViewWidgetBinding.inflate(layoutInflater, this, true)

        val a = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.SimpleChatView,
            0, 0
        )

        setAttributes(a)

        a.recycle()

        setRecyclerView()

        setListeners()

        binding.btnAdd.setOnClickListener {
            if (isMoreLayoutVisible) {
                hideMoreLayout()
            } else {
                showMoreLayout()
            }
        }

        binding.btnSend.setOnClickListener {
            if (isMoreLayoutVisible)
                hideMoreLayout()
            val message = binding.edtMessage.text.toString()
            binding.edtMessage.text.clear()
            if (message.isNotBlank() && message.isNotEmpty())
                onMessageSendListener?.invoke(message)
        }

        binding.edtMessage.setOnClickListener { hideMoreLayout() }
        binding.edtMessage.setOnEditorActionListener { _, _, _ ->
            if (isMoreLayoutVisible)
                hideMoreLayout()
            true
        }


    }

    private fun setAttributes(a: TypedArray) {
        setShowAddButton(a.getBoolean(R.styleable.SimpleChatView_showAddButton, true))
        setAddButtonColor(
            a.getColor(
                R.styleable.SimpleChatView_addButtonColor,
                Color.parseColor("#FF548E")
            )
        )
        setChatViewBackground(
            a.getColor(
                R.styleable.SimpleChatView_chatViewBackgroundColor,
                context.getColor(android.R.color.white)
            )
        )
        setChatInputBackgroundColor(
            a.getColor(
                R.styleable.SimpleChatView_chatInputBackgroundColor,
                Color.parseColor("#F3F3F3")
            )
        )
        setChatInputBackground(
            a.getInt(
                R.styleable.SimpleChatView_chatInputBackground,
                R.drawable.chat_input_shape
            )
        )
        setHintTextColor(
            a.getColor(
                R.styleable.SimpleChatView_hintTextColor,
                Color.parseColor("#929292")
            )
        )
        setTextColor(a.getColor(R.styleable.SimpleChatView_textColor, Color.BLACK))
        setHint(a.getString(R.styleable.SimpleChatView_hint))
        setSendButtonColor(
            a.getColor(
                R.styleable.SimpleChatView_sendButtonColor,
                Color.parseColor("#0174CF")
            )
        )
        setShowImageButton(a.getBoolean(R.styleable.SimpleChatView_showImageButton, true))
        setShowVideoButton(a.getBoolean(R.styleable.SimpleChatView_showVideoButton, true))
        setShowCameraButton(a.getBoolean(R.styleable.SimpleChatView_showCameraButton, true))
        setShowSenderLayout(a.getBoolean(R.styleable.SimpleChatView_showSenderLayout, true))
    }

    fun setShowSenderLayout(boolean: Boolean) {
        if (boolean)
            binding.layoutChatInputHolder.visibility = VISIBLE
        else
            binding.layoutChatInputHolder.visibility = GONE
    }

     fun setShowCameraButton(boolean: Boolean) {
        if (boolean)
            binding.imgCamera.visibility = VISIBLE
        else
            binding.imgCamera.visibility = GONE
    }

     fun setShowVideoButton(boolean: Boolean) {
        if (boolean)
            binding.imgVideo.visibility = VISIBLE
        else
            binding.imgVideo.visibility = GONE
    }

     fun setShowImageButton(boolean: Boolean) {
        if (boolean)
            binding.imgImage.visibility = VISIBLE
        else
            binding.imgImage.visibility = GONE
    }

    fun setSendButtonColor(color: Int) {
        binding.btnSend.background.setTint(color)
    }

    fun setHint(string: String?) {
        val hint = string ?: "Type message"
        binding.edtMessage.hint = hint
    }

    fun setTextColor(color: Int) {
        binding.edtMessage.setTextColor(color)
    }

    fun setHintTextColor(color: Int) {
        binding.edtMessage.setHintTextColor(color)
    }

    fun setChatInputBackground(int: Int) {
        binding.layoutChatInputHolder.setBackgroundResource(int)
        binding.edtMessage.setBackgroundResource(int)
    }

    fun setChatInputBackgroundColor(color: Int) {
        binding.edtMessage.background.setTint(color)
        binding.layoutChatInputHolder.background.setTint(color)
    }

    fun setAddButtonColor(color: Int) {
        binding.btnAdd.background.setTint(color)
    }

    fun setShowAddButton(boolean: Boolean) {
        if (boolean)
            binding.btnAdd.visibility = VISIBLE
        else
            binding.btnAdd.visibility = GONE
    }

    fun setChatViewBackground(color: Int) {
        binding.simpleChatView.setBackgroundColor(color)
    }

    private fun showMoreLayout() {
        binding.btnAdd.setImageResource(R.drawable.ic_baseline_close_24)
        binding.moreLayout.visibility = VISIBLE
        isMoreLayoutVisible = true
    }

    private fun setListeners() {
        //adapter listeners
        simpleChatAdapter.setOnChatImageClickListener { onChatImageClickListener?.invoke(it) }
        simpleChatAdapter.setOnChatUserImageClickListener { onChatUserImageClickListener?.invoke(it) }
        simpleChatAdapter.setOnChatUsernameClickListener { onChatUsernameClickListener?.invoke(it) }
        simpleChatAdapter.setOnChatVideoClickListener { onChatVideoClickListener?.invoke(it) }
        simpleChatAdapter.setOnMessageClickListener { onMessageClickListener?.invoke(it) }

        //SimpleChatView listeners
        binding.imgImage.setOnClickListener {
            hideMoreLayout()
            onSelectImageClickListener?.invoke()
        }
        binding.imgVideo.setOnClickListener {
            hideMoreLayout()
            onSelectVideoClickListener?.invoke()
        }
        binding.imgCamera.setOnClickListener {
            hideMoreLayout()
            onCameraClickListener?.invoke()
        }

    }

    private fun hideMoreLayout() {
        binding.btnAdd.setImageResource(R.drawable.ic_baseline_add_24)
        binding.moreLayout.visibility = INVISIBLE
        isMoreLayoutVisible = false
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.stackFromEnd = true
        binding.rvChats.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = simpleChatAdapter
        }
    }

    fun addMessage(chatMessageList: ArrayList<ChatMessage>) {
        simpleChatAdapter.addData(chatMessageList)
    }

    fun addMessage(chatMessage: ChatMessage) {
        simpleChatAdapter.addData(chatMessage)
        binding.rvChats.smoothScrollToPosition(simpleChatAdapter.itemCount)
    }

    fun remove(chatMessage: ChatMessage) = simpleChatAdapter.remove(chatMessage)

    fun remove(position: Int) = simpleChatAdapter.remove(position)

    fun clearMessages() = simpleChatAdapter.clearMessages()

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

    fun setOnMessageSendListener(listener: (String) -> Unit) {
        onMessageSendListener = listener
    }

    fun setOnMessageClickListener(listener: (ChatMessage) -> Unit) {
        onMessageClickListener = listener
    }

    fun setOnSelectImageClickListener(listener: () -> Unit) {
        onSelectImageClickListener = listener
    }

    fun setOnSelectVideoClickListener(listener: () -> Unit) {
        onSelectVideoClickListener = listener
    }

    fun setOnSelectCameraClickListener(listener: () -> Unit) {
        onCameraClickListener = listener
    }

}