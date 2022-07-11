package com.pxh.myview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class WeRun : View {
    /**
     * 屏幕短边长.
     * 竖屏时为宽度,横屏时为高度.
     */
    private var length: Int = 0

    /**
     * 总步数条的默认颜色.
     */
    private var outerColor = Color.BLUE

    /**
     * 当前步数条的默认颜色.
     */
    private var innerColor = Color.YELLOW

    /**
     * 步数条的默认宽度.
     */
    private var borderWidth = 0f

    /**
     * 步数文字的默认颜色.
     */
    private var textColor = Color.CYAN

    /**
     * 步数文字的默认大小.
     */
    private var textSize = 0f

    /**
     * 一个用于显示整个view的正方形.
     */
    private val rect: RectF = RectF()

    /**
     * 一个用于显示步数文字的正方形.
     */
    private val rect1: Rect = Rect()

    /**
     * 当前步数.
     */
    var step = 0
        set(value) {
            invalidate()
            field = value
        }

    /**
     * 总步数条对应的最大步数.
     */
    var maxStep = 10000

    /**
     * 用于最大步数条上色的颜料对象.
     */
    private var outerPrint: Paint

    /**
     * 用于当前步数条上色的颜料对象.
     */
    private var innerPrint: Paint

    /**
     * 用于步数文字上色的颜料对象.
     */
    private var textPaint: Paint

    /**
     * 用于显示当前步数的String对象.
     */
    private var s = ""


    /**
     *
     * 构造器向下传递,保证每一个构造方法都走第三个构造方法.
     */
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //获取xml文件中设置的属性信息
        val array: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MyView)
        outerColor = array.getColor(R.styleable.MyView_outer_color, outerColor)
        innerColor = array.getColor(R.styleable.MyView_inner_color, innerColor)
        borderWidth = array.getDimension(R.styleable.MyView_border_width, borderWidth)
        textColor = array.getColor(R.styleable.MyView_text_color, textColor)
        textSize = array.getDimension(R.styleable.MyView_text_size, textSize)
        array.recycle()
        //初始化画笔对象
        outerPrint = Paint()
        outerPrint.apply {
            color = outerColor
            strokeWidth = borderWidth
            //抗锯齿选项
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        innerPrint = Paint()
        innerPrint.apply {
            color = innerColor
            strokeWidth = borderWidth
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        textPaint = Paint()
        textPaint.apply {
            color = textColor
            textSize = this@WeRun.textSize
        }

    }

    /**
     * 用于测量父控件分配给当前view的尺寸.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        //计算出最短的边,作为当前view的边长.
        if (width > height) setMeasuredDimension(height, height) else setMeasuredDimension(
            width,
            width
        )
        length = if (width > height) height else width
        val halfBorderWidth = borderWidth / 2
        rect.set(
            halfBorderWidth,
            halfBorderWidth,
            length.toFloat() - halfBorderWidth,
            length.toFloat() - halfBorderWidth
        )
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        //绘制最大步数条.
        canvas?.drawArc(rect, 135f, 270F, false, outerPrint)
        //绘制当前步数条.
        canvas?.drawArc(rect, 135f, 270F * step / maxStep, false, innerPrint)
        s = step.toString()
        //填充用于显示步数的矩形对象.
        textPaint.getTextBounds(s, 0, s.length, rect1)
        //获取字体信息.
        val fontMetricsInt = textPaint.fontMetricsInt
        //计算x,y,baseline,用于绘制text
        val dx = length / 2 - rect1.width() / 2
        val dy = fontMetricsInt.bottom - fontMetricsInt.top
        val baseline = length / 2 + dy / 2.toFloat()
        //绘制步数信息.
        canvas?.drawText(s, dx.toFloat(), baseline, textPaint)
    }

}