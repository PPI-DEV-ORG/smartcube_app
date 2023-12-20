package com.ppidev.smartcube.ui.components.card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.ui.theme.Purple40
import com.ppidev.smartcube.ui.theme.Typography
import com.ppidev.smartcube.utils.convertMillisecondsToHoursAndMinutes
import com.ppidev.smartcube.utils.extractNumberFromString

@Composable
fun CardServerInfo(
    modifier: Modifier = Modifier,
    avgCpuTemp: String,
    totalRam: String,
    fanSpeed: String,
    upTime: String,
    ramUsage: Float = 0f,
    ramFree: String
) {
    val splitNumberUpTime = extractNumberFromString(upTime)
    var upTimeFormat = "-"
    if (splitNumberUpTime != null) {
        upTimeFormat = convertMillisecondsToHoursAndMinutes(splitNumberUpTime)
    }

    Box(
        modifier = modifier
            .shadow(elevation = 2.dp, spotColor = Color.Gray, ambientColor = Color.Gray, clip = true)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Server Info",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Processor temp (avg): $avgCpuTemp",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Total Ram Space: $totalRam",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Fan Speed: $fanSpeed", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Up time: $upTimeFormat", style = MaterialTheme.typography.bodySmall)
            }

            AnimatedCircularProgressIndicator(
                currentValue = ramUsage,
                maxValue = 100,
                progressBackgroundColor = Color.LightGray,
                progressIndicatorColor = MaterialTheme.colorScheme.primary,
                midColor = Color.Red,
                midValue = 50,
                ramFree = ramFree
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardServerInfoPreview() {
    CardServerInfo(
        avgCpuTemp = "70 C",
        totalRam = "80 GB",
        fanSpeed = "3000 Rpm",
        upTime = "10h 5min",
        ramFree = "3 Gb"
    )
}

@Composable
private fun AnimatedCircularProgressIndicator(
    currentValue: Float,
    midValue: Int,
    maxValue: Int,
    progressBackgroundColor: Color,
    progressIndicatorColor: Color,
    midColor: Color,
    ramFree: String,
    modifier: Modifier = Modifier
) {

    val stroke = with(LocalDensity.current) {
        Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            ProgressStatus(
                currentValue = currentValue,
                progressBackgroundColor = progressBackgroundColor,
                progressIndicatorColor = progressIndicatorColor,
                midColor = midColor,
                midValue = midValue
            )

            val animateFloat = remember { Animatable(0f) }

            LaunchedEffect(animateFloat, currentValue) {
                animateFloat.animateTo(
                    targetValue = currentValue / maxValue.toFloat(),
                    animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
                )
            }

            Canvas(
                Modifier
                    .progressSemantics(currentValue / maxValue.toFloat())
                    .size(CircularIndicatorDiameter)
            ) {
                val startAngle = 90f
                val sweep: Float = animateFloat.value * 360f
                val diameterOffset = stroke.width / 2

                drawCircle(
                    color = progressBackgroundColor,
                    style = stroke,
                    radius = size.minDimension / 2.0f - diameterOffset
                )

                drawCircularProgressIndicator(
                    startAngle,
                    sweep,
                    if (currentValue <= midValue) progressIndicatorColor else midColor,
                    stroke
                )
            }
        }
        Text(text = "RAM", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Available $ramFree", style = MaterialTheme.typography.bodySmall)
    }
}


@Composable
private fun ProgressStatus(
    currentValue: Float,
    midValue: Int,
    progressBackgroundColor: Color,
    progressIndicatorColor: Color,
    midColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            val emphasisSpan =
                Typography.titleLarge.copy(
                    color = if (currentValue <= midValue) {
                        progressIndicatorColor
                    } else {
                        midColor
                    },
                    fontWeight = FontWeight.Bold
                ).toSpanStyle()
            val defaultSpan =
                Typography.bodyMedium.copy(
                    color = progressBackgroundColor,
                    fontWeight = FontWeight.Medium
                ).toSpanStyle()
            append(AnnotatedString("$currentValue", spanStyle = emphasisSpan))
            append(AnnotatedString(text = "%", spanStyle = defaultSpan))
        },
    )
}

private fun DrawScope.drawCircularProgressIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

private val CircularIndicatorDiameter = 84.dp