package ru.montgolfiere.searchquest

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation: Animation =
            AnimationUtils.loadAnimation(this, R.anim.infinity_rotate_animation)
        val image = findViewById<ImageView>(R.id.splash_image)
        image.startAnimation(animation)
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, ContainerActivity::class.java))
            finish()
        }
    }
}