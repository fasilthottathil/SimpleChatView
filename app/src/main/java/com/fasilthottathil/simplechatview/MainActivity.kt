package com.fasilthottathil.simplechatview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fasilthottathil.simplechatview.model.ChatMessage
import com.fasilthottathil.simplechatview.widget.SimpleChatView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var chatMessageList:ArrayList<ChatMessage> = arrayListOf()
    private val position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.MAGENTA))
        window.statusBarColor = Color.MAGENTA

        val simpleChatView = findViewById<SimpleChatView>(R.id.simpleChatView)

        //sending text message
        simpleChatView.addMessage(
            ChatMessage(
                UUID.randomUUID().toString(),//message id
                "Hello world",//message
                "John Doe",//username
                "http://www.maxspring.ch/images/cartoons/4.jpg",//profile url
                true,//for setting left or right position of the chat
                System.currentTimeMillis(),//timestamp
                SimpleChatView.TYPE_TEXT//message type
            )
        )

        //sending image message
        simpleChatView.addMessage(
            ChatMessage(
                UUID.randomUUID().toString(),//message id
                "http://www.maxspring.ch/images/cartoons/c71.png",//image url
                "John Doe",//username
                "http://www.maxspring.ch/images/cartoons/4.jpg",//profile url
                true,//for setting left or right position of the chat
                System.currentTimeMillis(),//timestamp
                SimpleChatView.TYPE_IMAGE//message type
            )
        )

        //sending video message
        simpleChatView.addMessage(
            ChatMessage(
                UUID.randomUUID().toString(),//message id
                "http://www.maxspring.ch/images/cartoons/Kultursponsoring.jpg",//video url
                "John Doe",//username
                "http://www.maxspring.ch/images/cartoons/4.jpg",//profile url
                true,//for setting left or right position of the chat
                System.currentTimeMillis(),//timestamp
                SimpleChatView.TYPE_VIDEO//message type
            )
        )


        //adding list of messages
        simpleChatView.addMessage(chatMessageList)


        //removing message
        simpleChatView.remove(position)

        simpleChatView.remove(ChatMessage())

        simpleChatView.clearMessages()//clears all the added messages


        simpleChatView.setOnMessageSendListener { message ->
            //user clicked on send message button
        }

        simpleChatView.setOnMessageClickListener { chatMessage ->
            //user clicked on the Text Message
        }

        simpleChatView.setOnChatImageClickListener { chatMessage ->
            //user clicked on the image message
        }

        simpleChatView.setOnChatVideoClickListener { chatMessage ->
            //user clicked on the video message
        }

        simpleChatView.setOnChatUserImageClickListener { chatMessage ->
            //user clicked on the profile pic
        }

        simpleChatView.setOnChatUsernameClickListener { chatMessage ->
            //user clicked on the username
        }


        simpleChatView.setOnSelectImageClickListener {
            //user clicked on the image selection button
        }

        simpleChatView.setOnSelectVideoClickListener {
            //user clicked on the video selection button
        }

        simpleChatView.setOnSelectCameraClickListener {
            //user clicked on the camera button
        }


        simpleChatView.setChatViewBackground(Color.WHITE)
        simpleChatView.setSendButtonColor(Color.BLUE)
        simpleChatView.setAddButtonColor(Color.RED)
        simpleChatView.setChatInputBackgroundColor(Color.LTGRAY)
        simpleChatView.setChatInputBackground(R.drawable.chat_input_shape)
        simpleChatView.setShowAddButton(true or false)
        simpleChatView.setShowSenderLayout(true or false)
        simpleChatView.setShowImageButton(true or false)
        simpleChatView.setShowVideoButton(true or false)
        simpleChatView.setShowCameraButton(true or false)
        simpleChatView.setHint("Type message...")
        simpleChatView.setHintTextColor(Color.parseColor("#929292"))
        simpleChatView.setTextColor(Color.BLACK)


    }
}