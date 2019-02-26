package com.example.holmi_production.customview

import android.content.Context
import android.graphics.Canvas
import android.util.TypedValue
import android.view.ViewGroup
import androidx.annotation.NonNull

class MyCustomViewGroup(context:Context?) : ViewGroup(context){

    private fun init(@NonNull context: Context?) {

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private  fun dpToPx(dp:Float): Float {
        val resources = resources
        val displayMetrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,displayMetrics)
    }
}