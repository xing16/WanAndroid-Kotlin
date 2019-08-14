package com.xing.wanandroid.activity

import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.xing.wanandroid.R
import com.xing.wanandroid.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var musicImgView: ImageView
    private lateinit var logoImgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        musicImgView = findViewById(R.id.iv_logo_music)
        logoImgView = findViewById(R.id.iv_splash_text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val musicDrawable = resources.getDrawable(R.drawable.vector_animator_wanandroid) as AnimatedVectorDrawable
            musicImgView.setImageDrawable(musicDrawable)
            musicDrawable.start()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                musicDrawable.registerAnimationCallback(AnimatorCallback())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    inner class AnimatorCallback : Animatable2.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
            super.onAnimationEnd(drawable)
            this@SplashActivity.logoImgView.visibility = View.VISIBLE
            this@SplashActivity.logoImgView.setImageResource(R.drawable.splash_text)
            gotoActivity()
        }
    }

    private fun gotoActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
