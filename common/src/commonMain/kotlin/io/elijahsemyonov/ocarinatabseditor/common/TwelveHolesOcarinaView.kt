package io.elijahsemyonov.ocarinatabseditor.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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

//kotlin USHort postfix

val TWELVE_HOLES_OCARINA_NOTES_BITSETS_LIST = arrayOf(
    A(3u) to 0b1111_1111_11_11u,
    ASharp(3u) to 0b1111_1111_10_11u,
    B(3u) to 0b1111_1111_01_11u,
    C(4u) to 0b1111_1111_00_11u,
    CSharp(4u) to 0b1111_1110_01_11u,
    D(4u) to 0b1111_1110_00_11u,
    DSharp(4u) to 0b1111_1100_01_11u,
    E(4u) to 0b1111_1100_00_11u,
    F(4u) to 0b1111_1000_00_11u,
    FSharp(4u) to 0b1111_0010_00_11u,
    G(4u) to 0b1111_0000_00_11u,
    GSharp(4u) to 0b1101_0010_00_11u,
    A(4u) to 0b1101_0000_00_11u,
    ASharp(4u) to 0b1001_0010_00_11u,
    B(4u) to 0b1001_0000_00_11u,
    C(5u) to 0b0001_0000_00_11u,
    CSharp(5u) to 0b0001_0010_00_01u,
    D(5u) to 0b0001_0000_00_01u,
    DSharp(5u) to 0b0001_0010_00_00u,
    E(5u) to 0b0001_0000_00_00u,
    F(5u) to 0b0000_0000_00_00u,
)
val TWELVE_HOLES_OCARINA_NOTES_BITSETS_MAP = TWELVE_HOLES_OCARINA_NOTES_BITSETS_LIST.toMap()

@Composable
fun TwelveHolesOcarinaWithNoteView(modifier: Modifier, note: Note) {
    val bitset = TWELVE_HOLES_OCARINA_NOTES_BITSETS_MAP[note]

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TwelveHolesOcarinaView(modifier, bitset)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = note.name)
    }
}

@Composable
fun TwelveHolesOcarinaView(modifier: Modifier, bitset: UInt?) {
    Canvas(modifier.aspectRatio(1f)) {
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


            if (bitset == null) {
                return@rotate
            }

            val isFilled = { holeBitOffset: Int ->
                bitset and (1u shl (11 - holeBitOffset)) != 0u
            }

            drawHole(0.29f, 0.45f, isFilled(0), false)
            drawHole(0.35f, 0.48f, isFilled(1), false)
            drawHole(0.41f, 0.49f, isFilled(2), false)
            drawHole(0.47f, 0.48f, isFilled(3), false)

            drawHole(0.53f, 0.54f, isFilled(4), false)
            drawHole(0.59f, 0.52f, isFilled(5), false)
            drawHole(0.65f, 0.52f, isFilled(6), false)
            drawHole(0.71f, 0.54f, isFilled(7), false)

            drawHole(0.35f, 0.53f, isFilled(8), true)
            drawHole(0.59f, 0.47f, isFilled(9), true)

            drawHole(0.32f, 0.68f, isFilled(10), false)
            drawHole(0.58f, 0.7f, isFilled(11), false)
        })

    }
}

@Preview
@Composable
fun TwelveHolesOcarinaViewPreview() {
    LazyVerticalGrid(columns = GridCells.Adaptive(200.dp), contentPadding = PaddingValues(32.dp)) {
        item {
            TwelveHolesOcarinaWithNoteView(Modifier.width(140.dp), A(0u))
        }

        items(TWELVE_HOLES_OCARINA_NOTES_BITSETS_LIST) { (note, _) ->
            TwelveHolesOcarinaWithNoteView(Modifier.width(140.dp), note)
        }
    }
}