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
        } finally {
            a.recycle()
        }
    }

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
        val rightPos = r - l - paddingHorizontal
        var widthTmp = paddingHorizontal
        var heightTmp = paddingVertical
        for(i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                if(i==0){
                    child.layout(paddingHorizontal,paddingVertical,paddingHorizontal + child.measuredWidth, paddingVertical + customChildHeight)
                    widthTmp+= child.measuredWidth + paddingHorizontal
                    continue
               }
                //Переход на новую строку
                if(widthTmp + child.measuredWidth >= rightPos){
                    heightTmp+= customChildHeight + paddingVertical
                    widthTmp = paddingHorizontal
                    child.layout(paddingHorizontal, heightTmp, paddingHorizontal + child.measuredWidth, heightTmp + customChildHeight)
                }
                else{
                    child.layout(widthTmp, heightTmp, widthTmp + child.measuredWidth, heightTmp + customChildHeight)
                }
                widthTmp+= child.measuredWidth + paddingHorizontal
            }
        }
    }

    private  fun dpToPx(dp:Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}

