package com.example.holmi_production.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
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
            val paddingInt = a.getInt(R.styleable.MyCustomViewGroup_paddingHorizontalDP, 10)
            paddingBetweenView = dpToPx(paddingInt)
        } finally {
            a.recycle()
        }
    }

    private var paddingHorizontal = dpToPx(8)
    private var paddingBetweenView = 0
    private val paddingVertical = dpToPx(8)
    private val heightChild = dpToPx(50)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val count = childCount
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var tWidth = paddingHorizontal
        var tHeight = paddingVertical + heightChild
        for (i in 0 until count){
            val child: View = getChildAt(i)
            if (child.visibility!= View.GONE){
                measureChild(child,child.measuredWidth,heightChild)
                if( i == 0 ){
                    tWidth += child.measuredWidth + paddingBetweenView
                    continue
                }
                //переход на новую строку
                if(tWidth + child.measuredWidth > width)
                {
                    tHeight+= heightChild + paddingVertical
                    tWidth = paddingHorizontal
                }
                tWidth+= child.measuredWidth + paddingBetweenView
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
                    child.layout(paddingHorizontal,paddingVertical,paddingHorizontal + child.measuredWidth, paddingVertical + heightChild)
                    widthTmp+= child.measuredWidth + paddingBetweenView
                    continue
               }
                //Переход на новую строку
                if(widthTmp + child.measuredWidth > rightPos){
                    heightTmp+= heightChild + paddingVertical
                    widthTmp = paddingHorizontal
                    child.layout(paddingHorizontal, heightTmp, paddingHorizontal + child.measuredWidth, heightTmp + heightChild)
                }
                else{
                    child.layout(widthTmp, heightTmp, widthTmp + child.measuredWidth, heightTmp + heightChild)
                }
                widthTmp+= child.measuredWidth + paddingBetweenView
            }
        }
    }

    private  fun dpToPx(dp:Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    //Всё что ниже для основного задания не нужно, но кажется нужно для доп. задания.
    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MyCustomViewGroup.LayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    class LayoutParams : ViewGroup.LayoutParams {

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {}

        constructor(source: ViewGroup.LayoutParams) : super(source) {}

        constructor(width: Int, height: Int) : super(width, height) {}
    }
}

