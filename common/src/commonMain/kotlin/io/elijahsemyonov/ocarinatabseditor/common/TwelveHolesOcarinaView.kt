package io.elijahsemyonov.ocarinatabseditor.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

fun DrawScope.drawBezierSpline(
    color: Color,
    spline: CubicBezierSpline,
    strokeWidth: Float = 1f,
    iterations: Int = 100
) {
    val tDelta = 1f / iterations.toFloat()

    var previousOffset = spline.getPoint(0f)

    for (i in 1..iterations) {
        val t = i * tDelta
        val offset = spline.getPoint(t)
        drawLine(color, previousOffset, offset, strokeWidth)
        previousOffset = offset
    }
}

@Composable
fun TwelveHolesOcarinaView(modifier: Modifier) {
    Canvas(modifier) {
        val size = size

        val offsetFromRelative = { x: Float, y: Float -> Offset(size.width * x, size.height * y) }

        val drawHole = { x: Float, y: Float, isFilled: Boolean, isSmall: Boolean ->
            drawCircle(
                color = Color.White,
                radius = if (isSmall) 0.015f * size.width else 0.023f * size.width,
                center = offsetFromRelative(x, y),
                style = Fill
            )

            drawCircle(
                color = Color.Black,
                radius = if (isSmall) 0.015f * size.width else 0.023f * size.width,
                center = offsetFromRelative(x, y),
                style = if (isFilled) Fill else Stroke(3f)
            )
        }

        rotate(-30f, offsetFromRelative(0.5f, 0.5f), block = {
            rotate(10f, offsetFromRelative(0.5f, 0.5f), block = {
                drawOval(
                    color = Color.LightGray,
                    topLeft = offsetFromRelative(0.2f, 0.37f),
                    size = Size(size.width * 0.6f, size.height * 0.26f),
                    style = Fill
                )

                drawOval(
                    color = Color.LightGray,
                    topLeft = offsetFromRelative(0.41f, 0.5f),
                    size = Size(size.width * 0.15f, size.height * 0.3f),
                    style = Fill
                )
            })


            drawHole(0.29f, 0.45f, false, false)
            drawHole(0.35f, 0.48f, false, false)
            drawHole(0.41f, 0.49f, false, false)
            drawHole(0.47f, 0.48f, false, false)

            drawHole(0.53f, 0.54f, false, false)
            drawHole(0.59f, 0.52f, false, false)
            drawHole(0.65f, 0.52f, false, false)
            drawHole(0.71f, 0.54f, false, false)

            drawHole(0.35f, 0.53f, false, true)
            drawHole(0.59f, 0.47f, false, true)

            drawHole(0.32f, 0.68f, false, false)
            drawHole(0.58f, 0.7f, false, false)
        })

    }
}

@Preview
@Composable
fun TwelveHolesOcarinaViewPreview() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TwelveHolesOcarinaView(Modifier.size(200.dp))
    }
}