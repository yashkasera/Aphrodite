package com.yashkasera.aphrodite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yashkasera.aphrodite.R
import com.yashkasera.aphrodite.databinding.FragmentWelcomeBinding

/**
 * @author yashkasera
 * Created 05/12/21 at 3:38 AM
 */
private const val TAG = "WelcomeFragment"

class WelcomeFragment(private val position: Int) : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    private val images = intArrayOf(
        R.drawable.ic_onboarding_1,
        R.drawable.ic_onboarding_2,
        R.drawable.ic_onboarding_3
    )

    private val texts = intArrayOf(
        R.string.on_boarding_1,
        R.string.on_boarding_2,
        R.string.on_boarding_3
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textView.text = getString(texts[position])
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    images[position]
                )
            )
        }
    }
}