package com.yashkasera.aphrodite.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.yashkasera.aphrodite.databinding.FragmentChatBinding
import com.yashkasera.aphrodite.model.Contact

/**
 * @author yashkasera
 * Created 04/12/21 at 9:39 PM
 */
private const val TAG = "ChatFragment"

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var chatMessageAdapter: ChatMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            Contact("Yash Kasera", "Yellow Racoon", Timestamp.now()),
            Contact("Dionne Briana", "Amaranth Ferret", Timestamp.now()),
            Contact("Alisha Giacinta", "Salmon Alligator", Timestamp.now()),
            Contact("Sianna Carrie", "Amber Wolf", Timestamp.now()),
            Contact("Consuela Deonne", "Moccasin Grouse", Timestamp.now()),
            Contact("Inga Molli", "Sapphire Stork", Timestamp.now()),
            Contact("Yevette Nita", "Purple Puffin", Timestamp.now()),
            Contact("Bryn Blondelle", "Gold Dog", Timestamp.now()),
            Contact("Calida Aurore", "Blue Rodent", Timestamp.now()),
            Contact("Kelcy Jillian", "Harlequin Tapir", Timestamp.now()),
            Contact("Angelica Timi", "Fuchsia Iguana", Timestamp.now()),
        )
        val contactAdapter = ContactsAdapter(object : ContactsAdapter.ChatClickListener {
            override fun onClick(contact: Contact) {
                Log.i(TAG, "onClick: ${contact.name}")
                if (contact.name != null) {
                    findNavController().navigate(
                        ChatFragmentDirections.actionNavigationChatToChatActivity(contact.name!!)
                    )
                }
            }
        })

        binding.root.adapter = contactAdapter
        contactAdapter.submitList(list)

    }

}