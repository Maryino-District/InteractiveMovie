package com.example.interactivemovieapp.Animations

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.view.marginLeft
import java.util.*

class ViewAnimation(view: View, root: ViewGroup) {
    private var speedX = Random().nextFloat() * 200
    private var speedY = Random().nextFloat() * 200

    fun animateAcrossScreen(view: View, root: ViewGroup, context: Context) {

        //root.viewTreeObserver.addOnGlobalLayoutListener(object:

          //  ViewTreeObserver.OnGlobalLayoutListener {
           // override fun onGlobalLayout() {
                var height = root.height - view.height
                var width = root.width - view.width
                var period = 100

                Timer().scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        view.x = speedX * period / 1000.0f + view.x
                        Log.d("tagbefore", "${view.x}")


                      //  view.x = speedX * 10 / 1000.0f + view.x
                      //  view.y = speedY * 10 / 1000.0f + view.y
                        Log.d("tagbeafter", "${view.x}")
                        view.animate().x(100f).y(100f).duration = 1000
                         (view.y <= 0 || view.y >= height)
                                    speedY *= -1
                                if (view.x <= 0 || view.x >= width)
                                    speedX *= -1


                    }
                },100L,period.toLong())

            }
        //})


    //}

}