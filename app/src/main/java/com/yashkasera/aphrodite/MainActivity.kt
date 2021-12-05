package com.yashkasera.aphrodite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*
import com.google.android.gms.nearby.messages.Strategy.DISCOVERY_MODE_BROADCAST
import com.google.android.gms.nearby.messages.Strategy.TTL_SECONDS_MAX
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.yashkasera.aphrodite.databinding.ActivityMainBinding
import com.yashkasera.aphrodite.databinding.NavigationDrawerBinding
import com.yashkasera.aphrodite.util.SharedPrefUtil.getPrefs
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.nio.charset.Charset

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), FirebaseAuth.IdTokenListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val PUB_SUB_STRATEGY = Strategy.Builder()
        .setDiscoveryMode(DISCOVERY_MODE_BROADCAST)
        .setTtlSeconds(TTL_SECONDS_MAX).build()
    private lateinit var message: Message
    private lateinit var messageListener: MessageListener
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        authToken = intent.getStringExtra(AUTH_TOKEN) ?: ""
        Log.wtf(TAG, "onCreate: $authToken")

        val navView: BottomNavigationView = binding.bottomNavigation
        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_requests,
                R.id.navigation_chat
            )
        )
        binding.apply {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            navigationDrawer.bindDrawer()
            toggle.syncState()
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        message = Message(Build.MODEL.toByteArray(Charset.forName("UTF-8")))

        messageListener = object : MessageListener() {
            override fun onFound(message: Message) {
                Log.d(TAG, "Found message: " + String(message.content));
            }

            override fun onLost(message: Message) {
                Log.d(TAG, "Lost sight of message: " + String(message.content));
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Nearby.getMessagesClient(this).unpublish(message)
        Nearby.getMessagesClient(this).unsubscribe(messageListener)
    }

    private fun publish() {
        val options = PublishOptions.Builder()
            .setStrategy(PUB_SUB_STRATEGY)
            .build()

        Nearby.getMessagesClient(this).publish(message, options).addOnSuccessListener {
            Log.d(TAG, "publish() returned: $message")
        }.addOnFailureListener {
            Log.e(TAG, "publish: Failure", it)
        }
    }

    private fun subscribe() {
        val options = SubscribeOptions.Builder()
            .setStrategy(PUB_SUB_STRATEGY)
            .setCallback(object : SubscribeCallback() {
                override fun onExpired() {
                    super.onExpired()
                    runOnUiThread {
//                        binding.subscribeSwitch.isChecked = false
                    }
                }
            }).build()

        Nearby.getMessagesClient(this).subscribe(messageListener, options).addOnSuccessListener {
            Log.i(TAG, "subscribe: Success")
        }.addOnFailureListener {
            Log.e(TAG, "subscribe: Failure", it)
        }
    }

    private fun unsubscribe() {
        Nearby.getMessagesClient(this).unsubscribe(messageListener).addOnSuccessListener {
            Log.i(TAG, "unsubscribe: Success")
        }.addOnFailureListener {
            Log.e(TAG, "unsubscribe: Failure", it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
//        navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onIdTokenChanged(p0: FirebaseAuth) {
        lifecycleScope.launch {
            try {
                val res = p0.getAccessToken(true).await()
                res.token?.let {
                    authToken = it
                    Log.i(TAG, "onIdTokenChanged: $it")
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                Intent(this@MainActivity, AuthActivity::class.java)
                    .also {
                        startActivity(it)
                        finish()
                    }
            }
        }
    }

    fun NavigationDrawerBinding.bindDrawer() {
        navigationProfile.setOnClickListener {
            navigate(R.id.navigation_profile)
        }
        navigationFaq.setOnClickListener {
            navigate(R.id.navigation_faq)
        }
        navigationCoupons.setOnClickListener {
            navigate(R.id.navigation_coupons)
        }
        navigationHistory.setOnClickListener {
            navigate(R.id.navigation_history)
        }
        logout.setOnClickListener {
            getPrefs(this@MainActivity).edit().clear().apply()
            FirebaseAuth.getInstance().signOut()
        }
    }

    private fun navigate(id: Int) {
        navController.navigate(id)
    }

    companion object {
        const val AUTH_TOKEN = "auth_token"
        var authToken = ""
        suspend fun fetchToken(): String? {
            val res = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()
            return res?.token
        }
    }
}