package com.yashkasera.aphrodite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.yashkasera.aphrodite.MainActivity.Companion.AUTH_TOKEN
import com.yashkasera.aphrodite.databinding.ActivitySplashBinding
import com.yashkasera.aphrodite.util.IS_FIRST_LAUNCH
import com.yashkasera.aphrodite.util.SharedPrefUtil.getPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.logo.animation = animation
        val user = FirebaseAuth.getInstance().currentUser
        lifecycleScope.launch {
            delay(500)
            Intent(
                this@SplashActivity,
                if (getPrefs(this@SplashActivity).getBoolean(
                        IS_FIRST_LAUNCH,
                        true
                    )
                ) WelcomeActivity::class.java
                else when (user) {
                    null -> AuthActivity::class.java
                    else -> MainActivity::class.java
                }
            ).also {
                if (user != null) {
                    val res = user.getIdToken(true).await()
                    it.putExtra(AUTH_TOKEN, res.token ?: "")
                }
                startActivity(it)
                finish()
            }
        }
    }
}