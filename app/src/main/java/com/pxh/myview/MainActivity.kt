package com.pxh.myview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val weRun: WeRun = findViewById(R.id.myView)
        //当前步数.
        weRun.step = 9000
        val animator: ValueAnimator = ObjectAnimator.ofInt(weRun.step)
        //动画时长.
        animator.duration = 1000
        animator.addUpdateListener {
            weRun.step = it.animatedValue as Int
        }
        animator.start()
    }
}