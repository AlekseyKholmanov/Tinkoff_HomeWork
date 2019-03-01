package com.example.holmi_production.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull

class MyCustomViewGroup:ViewGroup
{
    constructor(context: Context) : super(context) {

    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initAttrs(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initAttrs(context, attrs)
    }

    fun initAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomViewGroup)
        val padding = typedArray.getIndex(R.styleable.MyCustomViewGroup_mcvg_paddingHorizontalDP)
        typedArray.recycle()
    }

    private val paddingHorizontal = 0
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
                val childWidthMeas = child.measuredWidth
                val childHeightMeas = heightChild
                measureChild(child,childWidthMeas,childHeightMeas)
                val layoutParams = child.layoutParams as LayoutParams

                if( i == 0 ){
                    tWidth += child.measuredWidth + layoutParams.horizontalPadding
                    continue
                }
                if(tWidth + child.measuredWidth > width)
                {
                    tHeight+= heightChild + paddingVertical
                    tWidth = paddingHorizontal
                }
                tWidth+= child.measuredWidth + layoutParams.horizontalPadding
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
                val layoutParams = child.layoutParams as LayoutParams
                if(i==0){
                    child.layout(paddingHorizontal,paddingVertical,paddingHorizontal + child.measuredWidth, paddingVertical + heightChild)
                    widthTmp+= child.measuredWidth + layoutParams.horizontalPadding
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
                widthTmp+= child.measuredWidth + layoutParams.horizontalPadding
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }

    private  fun dpToPx(dp:Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    public class LayoutParams:ViewGroup.LayoutParams{

        var horizontalPadding = 0

        constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
            val typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyCustomViewGroup)
            horizontalPadding = typedArray.getInt(R.styleable.MyCustomViewGroup_mcvg_paddingHorizontalDP, 10)
            typedArray.recycle()
        }
        constructor(params: LayoutParams) : super(params){
            if(params is LayoutParams){
                val MCVGparams = params as LayoutParams
                horizontalPadding = MCVGparams.horizontalPadding

            }
        }
    }


}