package com.yashkasera.aphrodite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yashkasera.aphrodite.databinding.FragmentFaqBinding

/**
 * @author yashkasera
 * Created 05/12/21 at 5:57 AM
 */
private const val TAG = "FaqFragment"

class FaqFragment : Fragment() {
    private lateinit var binding: FragmentFaqBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}