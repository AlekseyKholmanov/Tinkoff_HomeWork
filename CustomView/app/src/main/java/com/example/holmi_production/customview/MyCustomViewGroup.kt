package com.example.holmi_production.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
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

        for (i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                val childWidthMeas =MeasureSpec.makeMeasureSpec(child.width + 2 * paddingHorizontal, MeasureSpec.AT_MOST)
                val childHeightMeas = MeasureSpec.makeMeasureSpec(heightChild+ 2*paddingVertical, MeasureSpec.AT_MOST)
                child.measure(childWidthMeas,childHeightMeas)
            }
        }
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
       setMeasuredDimension(width,height/2)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val leftPos = paddingHorizontal
        val rightPos = r-l-paddingHorizontal
        val topPos = paddingVertical
        val botPos = b-t-paddingVertical
        var widthTmp = leftPos
        var heightTmp = topPos
        for(i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                if(i==0){
                    child.layout(leftPos,topPos,leftPos + child.width, topPos + child.height)
                }
                widthTmp+=child.width
                if(widthTmp > rightPos){
                    heightTmp += child.height + topPos
                    widthTmp = child.width + leftPos
                    child.layout(leftPos,heightTmp, widthTmp, heightTmp+child.width)
                }
                else{
                    child.layout(widthTmp + leftPos , heightTmp , widthTmp + child.width + leftPos, heightTmp + child.height )
                    widthTmp +=child.width + leftPos
                }
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