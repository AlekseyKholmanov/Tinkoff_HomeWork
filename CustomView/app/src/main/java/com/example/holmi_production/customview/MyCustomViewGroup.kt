package com.example.holmi_production.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull

class MyCustomViewGroup:ViewGroup
{
    constructor(context: Context) : super(context) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context)
    }

    val paddingHorizontal = dpToPx(8)
    val paddingVertical = dpToPx(8)
    val heightChild = dpToPx(30)
    private fun init(@NonNull context: Context?) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val count = childCount
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                val childWidthMeas = width - 2 * paddingHorizontal
                val childHeightMeas = heightChild + paddingVertical
                measureChild(child,childWidthMeas,childHeightMeas)
            }
        }

       setMeasuredDimension(width,height/2)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val leftPos = paddingHorizontal
        val rightPos = r-l-paddingHorizontal
        val topPos = paddingVertical+t
        val botPos = b-t-paddingVertical
        var widthTmp = leftPos
        var heightTmp = topPos
        for(i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                if(i==0){
                    child.layout(leftPos,topPos,leftPos + child.measuredWidth, topPos + child.measuredHeight)
                    Log.d("qwerty", "Number:$i L:$leftPos T:$topPos R:$right B:$bottom  tempWidth:$widthTmp, temH:$heightTmp")
                    widthTmp += child.measuredWidth + leftPos
                    continue
               }
                //Переход на новую строку
                if(widthTmp + child.measuredWidth > rightPos){
                    heightTmp += child.measuredHeight + paddingHorizontal
                    widthTmp = leftPos
                    child.layout(leftPos, heightTmp, leftPos + child.measuredWidth, heightTmp + child.measuredHeight)
                    Log.d("qwerty", "Number:$i || L:$leftPos || T:$heightTmp || R:$right || B:$bottom || tempWidth:$widthTmp || tempH:$heightTmp || childWidt: ${child.measuredWidth}")
                }
                else{
                    child.layout(widthTmp, heightTmp, widthTmp + child.measuredWidth, heightTmp + child.measuredHeight)
                    Log.d("qwerty", "Number:$i || L:$left || T:$top || R:$right || B:$bottom || tempWidth:$widthTmp || temH:$heightTmp || childWidt: ${child.measuredWidth}")
                }
                widthTmp += child.measuredWidth + leftPos
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }

    private  fun dpToPx(dp:Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}