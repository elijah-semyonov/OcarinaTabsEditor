package io.elijahsemyonov.ocarinatabseditor.common

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow

data class CubicBezierSpline(val start: Offset, val end: Offset, val control1: Offset, val control2: Offset) {
    fun getPoint(t: Float): Offset {
        val oneMinusT = 1 - t
        val oneMinusTSquared = oneMinusT.pow(2)
        val oneMinusTCubed = oneMinusTSquared * oneMinusT
        val tSquared = t.pow(2)
        val tCubed = tSquared * t

        val x = start.x * oneMinusTCubed +
                3 * control1.x * oneMinusTSquared * t +
                3 * control2.x * oneMinusT * tSquared +
                end.x * tCubed

        val y = start.y * oneMinusTCubed +
                3 * control1.y * oneMinusTSquared * t +
                3 * control2.y * oneMinusT * tSquared +
                end.y * tCubed

        return Offset(x, y)
    }
}