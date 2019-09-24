package com.xing.wanandroid.main

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.utils.gotoActivity

class SplashActivity : BaseActivity() {

//    private lateinit var musicImgView: ImageView
//    private lateinit var logoImgView: ImageView
    private lateinit var logoLottieView: LottieAnimationView

    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        logoLottieView = findViewById(R.id.lav_logo)
        logoLottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                gotoMainActivity()
            }
        })
//        musicImgView = findViewById(R.id.iv_logo_music)
//        logoImgView = findViewById(R.id.iv_splash_text)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val musicDrawable =
//                resources.getDrawable(R.drawable.vector_animator_wanandroid) as AnimatedVectorDrawable
//            musicImgView.setImageDrawable(musicDrawable)
//            musicDrawable.start()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                musicDrawable.registerAnimationCallback(AnimatorCallback())
//            }
//        }
    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    inner class AnimatorCallback : Animatable2.AnimationCallback() {
//        override fun onAnimationEnd(drawable: Drawable?) {
//            super.onAnimationEnd(drawable)
//            this@SplashActivity.logoImgView.visibility = View.VISIBLE
//            this@SplashActivity.logoImgView.setImageResource(R.drawable.splash_text)
//            gotoActivity()
//        }
//    }

    private fun gotoMainActivity() {
        gotoActivity(this, MainActivity().javaClass)
        finish()
    }
}
