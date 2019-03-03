package com.example.holmi_production.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
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
            val customGravity = a.getString(R.styleable.MyCustomViewGroup_cvg_gravity)
            if(customGravity == null)
                mGravity = GRAVITY_LEFT
            else
                mGravity = customGravity
        } finally {
            a.recycle()
        }
    }
    private var mGravity =""
    private val GRAVITY_LEFT = "left"
    private val GRAVITY_RIGHT = "right"
    private var paddingHorizontal = 0
    private var customChildHeight = 0
    private var paddingVertical = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val count = childCount
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var tWidth = paddingHorizontal
        var tHeight = paddingVertical + customChildHeight
        for (i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){

                measureChild(child,child.measuredWidth,customChildHeight)
                if( i == 0 ){
                    tWidth += child.measuredWidth + paddingHorizontal
                    continue
                }
                //переход на новую строку
                if(tWidth + child.measuredWidth >= width)
                {
                    tHeight+= customChildHeight + paddingVertical
                    tWidth = paddingHorizontal
                }
                tWidth+= child.measuredWidth + paddingHorizontal
            }
        }
       setMeasuredDimension(width,tHeight + paddingVertical)
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
                 usedWidth+= paddingHorizontal
             }
        }
    }

    private  fun dpToPx(dp:Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}

