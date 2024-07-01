package com.berfinilik.shopapp.activity

import android.os.Bundle
import com.berfinilik.shopapp.Helper.ChatbotService
import com.berfinilik.shopapp.databinding.ChatbotBinding

class ChatBotActivity : BaseActivity() {

    private lateinit var binding: ChatbotBinding
    private lateinit var chatbotService: ChatbotService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatbotService = ChatbotService(this)

        binding.sendButton.setOnClickListener {
            val message = binding.userInput.text.toString()
            if (message.isNotEmpty()) {
                chatbotService.sendMessageToDialogflow(message) { response ->
                    runOnUiThread {
                        binding.chatOutput.text = response
                    }
                }
            }

        }
        binding.buttonTemizle.setOnClickListener {
            binding.userInput.text.clear()
            binding.chatOutput.text = ""
        }
    }
}