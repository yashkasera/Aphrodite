package com.yashkasera.aphrodite.ui.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yashkasera.aphrodite.databinding.FragmentRequestsBinding
import com.yashkasera.aphrodite.databinding.ItemRequestBinding
import com.yashkasera.aphrodite.model.HelpRequest

/**
 * @author yashkasera
 * Created 04/12/21 at 9:39 PM
 */
private const val TAG = "RequestsFragment"

class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestAdapter = RequestAdapter()
        binding.recyclerView.adapter = requestAdapter
        val list = listOf(
            HelpRequest("Ede Tracy","6.42km away"),
            HelpRequest("Alexine Giana","9.72km away"),
            HelpRequest("Sallyanne Kala","3.49km away"),
            HelpRequest("Arabel Nonnah","9.73km away"),
            HelpRequest("Isidora Robbyn","8.69km away"),
            HelpRequest("Orelie Marena","3.01km away"),
            HelpRequest("Abra Ilse","4.30km away"),
            HelpRequest("Alana Madelaine","7.31km away"),
            HelpRequest("Florella Wendeline","0.45km away"),
            HelpRequest("Pamelina Reine","7.06km away"),
        )
        requestAdapter.submitList(list)
    }
}