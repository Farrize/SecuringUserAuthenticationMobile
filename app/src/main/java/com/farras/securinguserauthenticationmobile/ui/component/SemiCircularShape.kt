package com.farras.securinguserauthenticationmobile.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.Path

@Composable
fun SemiCircularBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val radius = size.width.coerceAtMost(size.height) / 2f
        val arc = Path().apply {
            arcTo(
                rect = Rect(0f, 0f, radius * 2, radius * 2),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            close()
        }
        clipPath(arc) {
            drawRect(Color.Blue)
        }
    }
}

