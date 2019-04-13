
package com.edu.uabc.appm.mytictactoe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BoardView : View {

     var widthBoard: Int = 0
     var heightBoard: Int = 0
     var eltW: Int = 0
     var eltH: Int = 0
     var gridPaint: Paint? = null
    var oPaint: Paint? = null
    var xPaint: Paint? = null

    private var gameEngine: GameEngine? = null
    private var activity: MainActivity? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        gridPaint = Paint()
        oPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        oPaint?.color = Color.RED
        oPaint?.style = Paint.Style.STROKE
        oPaint?.strokeWidth = ELT_STROKE_WIDTH.toFloat()
        xPaint = Paint(oPaint)
        xPaint?.color = Color.BLUE
    }

    fun setMainActivity(a: MainActivity) {
        activity = a
    }

    fun setGameEngine(g: GameEngine) {
        gameEngine = g
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        heightBoard = View.MeasureSpec.getSize(heightMeasureSpec)
        widthBoard = View.MeasureSpec.getSize(widthMeasureSpec)
        eltW = (widthBoard - LINE_THICK) / 3
        eltH = (heightBoard - LINE_THICK) / 3

        setMeasuredDimension(widthBoard, heightBoard)
    }

    override fun onDraw(canvas: Canvas) {
        drawGrid(canvas)
        drawBoard(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!gameEngine!!.isEnded && event.action == MotionEvent.ACTION_DOWN) {
            val x = (event.x / eltW).toInt()
            val y = (event.y / eltH).toInt()
            var win = gameEngine!!.play(x, y)
            invalidate()

            if (win != ' ') {
                activity!!.gameEnded(win)
            } else {
                // computer plays ...
                win = gameEngine!!.computer()
                invalidate()

                if (win != ' ') {
                    activity!!.gameEnded(win)
                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun drawBoard(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                drawElt(canvas, gameEngine!!.getElt(i, j), i, j)
            }
        }
    }

    private fun drawGrid(canvas: Canvas) {
        for (i in 0..1) {
            // vertical lines
            val left = (eltW * (i + 1)).toFloat()
            val right = left + LINE_THICK
            val top = 0f
            val bottom = heightBoard.toFloat()

            canvas.drawRect(left, top, right, bottom, gridPaint)

            // horizontal lines
            val left2 = 0f
            val right2 = widthBoard.toFloat()
            val top2 = (eltH * (i + 1)).toFloat()
            val bottom2 = top2 + LINE_THICK

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint)
        }
    }

    private fun drawElt(canvas: Canvas, c: Char, x: Int, y: Int) {
        if (c == 'O') {
            val cx = (eltW * x + eltW / 2).toFloat()
            val cy = (eltH * y + eltH / 2).toFloat()

            canvas.drawCircle(cx, cy, (Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2).toFloat(), oPaint)

        } else if (c == 'X') {
            val startX = (eltW * x + ELT_MARGIN).toFloat()
            val startY = (eltH * y + ELT_MARGIN).toFloat()
            val endX = startX + eltW - ELT_MARGIN * 2
            val endY = startY + eltH - ELT_MARGIN

            canvas.drawLine(startX, startY, endX, endY, xPaint)

            val startX2 = (eltW * (x + 1) - ELT_MARGIN).toFloat()
            val startY2 = (eltH * y + ELT_MARGIN).toFloat()
            val endX2 = startX2 - eltW + ELT_MARGIN * 2
            val endY2 = startY2 + eltH - ELT_MARGIN

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint)
        }
    }

    companion object {

        private val LINE_THICK = 5
        private val ELT_MARGIN = 20
        private val ELT_STROKE_WIDTH = 15
    }

}

