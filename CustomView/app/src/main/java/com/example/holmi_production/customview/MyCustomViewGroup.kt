package com.example.holmi_production.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

class MyCustomViewGroup:ViewGroup
{
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initAttrs(context,attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initAttrs(context,attrs, defStyleAttr)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet , defStyleAttr: Int) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.MyCustomViewGroup, 0, 0)
        try {
            paddingHorizontal = a.getDimensionPixelSize(R.styleable.MyCustomViewGroup_cvg_padding_horizontal, dpToPx(10))
            customChildHeight = a.getDimensionPixelOffset(R.styleable.MyCustomViewGroup_cvg_height, dpToPx(20))
            paddingVertical = a.getDimensionPixelOffset(R.styleable.MyCustomViewGroup_cvg_padding_vertical, dpToPx(10))
            mGravity = a.getInt(R.styleable.MyCustomViewGroup_cvg_gravity, 0)

        } finally {
            a.recycle()
        }
    }
    private var mGravity = 0
    private val GRAVITY_RIGHT = 1
    private var paddingHorizontal = 0
    private var customChildHeight = 0
    private var paddingVertical = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val count = childCount
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var tWidth = width - paddingHorizontal
        var tHeight = paddingVertical + customChildHeight
        var usedWidth = 0
        for (i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                usedWidth+=child.measuredWidth
                measureChild(child,child.measuredWidth,customChildHeight)
                //переход на новую строку
                if(usedWidth >= tWidth)
                {
                    tHeight += customChildHeight + paddingVertical
                    usedWidth = child.measuredWidth + paddingHorizontal
                }
                usedWidth+= paddingHorizontal
            }
        }
        Log.d("qwerty","gravity  $mGravity, width: $width,  height: $tHeight")
       setMeasuredDimension(width,tHeight + paddingVertical )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r-l-paddingHorizontal
        var tHeight = paddingVertical
        var usedWidth = paddingHorizontal
        for(i in 0 until count){
            val child: View = getChildAt(i)
             if (child.visibility!= View.GONE){
                 //вычисление длины ряда с текущим ребёнком
                 usedWidth+=child.measuredWidth

                 //проверка на переход
                 if(usedWidth >= width)
                 {
                     tHeight += customChildHeight + paddingVertical
                     usedWidth = child.measuredWidth + paddingHorizontal
                 }
                 var LeftPos = if (mGravity == GRAVITY_RIGHT) width - usedWidth else usedWidth-child.measuredWidth
                 var RightPos = if(mGravity == GRAVITY_RIGHT) width - usedWidth + child.measuredWidth else usedWidth
                 child.layout(LeftPos,tHeight,RightPos,tHeight+customChildHeight)
                 var top = tHeight+customChildHeight
                 Log.d("qwerty","gravit: $mGravity, left: $LeftPos, leftH: $tHeight, right: $RightPos,  rightH: $top, element: $i")
                 usedWidth+= paddingHorizontal
             }
        }
    }

    private  fun dpToPx(dp:Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}

