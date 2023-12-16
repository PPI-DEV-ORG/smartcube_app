package com.ppidev.smartcube.ui.components


import android.graphics.Typeface
import android.text.Layout
import android.text.TextUtils
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shape.chartShape
import com.patrykandpatrick.vico.compose.component.shape.shader.toDynamicShader
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.extension.pixelSize
import com.patrykandpatrick.vico.core.DEF_LABEL_LINE_COUNT
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.OverlayingComponent
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.dimensions.Dimensions
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.dimensions.emptyDimensions
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.marker.Marker

public typealias ChartShape = com.patrykandpatrick.vico.core.component.shape.Shape

/**
 * Creates and remembers a [LineComponent] with the specified properties.
 */
@Composable
public fun rememberLineComponent(
    color: Color = Color.Black,
    thickness: Dp,
    shape: ChartShape,
    dynamicShader: DynamicShader? = null,
    margins: Dimensions = emptyDimensions(),
    strokeWidth: Dp = 0.dp,
    strokeColor: Color = Color.Transparent,
): LineComponent =
    remember(
        color,
        thickness,
        shape,
        dynamicShader,
        margins,
        strokeWidth,
        strokeColor,
    ) {
        LineComponent(
            color = color.toArgb(),
            thicknessDp = thickness.value,
            shape = shape,
            dynamicShader = dynamicShader,
            margins = margins,
            strokeWidthDp = strokeWidth.value,
            strokeColor = strokeColor.toArgb(),
        )
    }

/**
 * Creates and remembers a [LineComponent] with the specified properties.
 */
@Composable
public fun rememberLineComponent(
    color: Color = Color.Black,
    thickness: Dp = DefaultDimens.COLUMN_WIDTH.dp,
    shape: Shape = RectangleShape,
    dynamicShader: DynamicShader? = null,
    margins: Dimensions = emptyDimensions(),
    strokeWidth: Dp = 0.dp,
    strokeColor: Color = Color.Transparent,
): LineComponent =
    rememberLineComponent(
        color = color,
        thickness = thickness,
        shape = shape.chartShape(),
        dynamicShader = dynamicShader,
        margins = margins,
        strokeWidth = strokeWidth,
        strokeColor = strokeColor,
    )

/**
 * Creates and remembers a [ShapeComponent] with the specified properties.
 */
@Composable
public fun rememberShapeComponent(
    shape: ChartShape = Shapes.rectShape,
    color: Color = Color.Black,
    dynamicShader: DynamicShader? = null,
    margins: Dimensions = emptyDimensions(),
    strokeWidth: Dp = 0.dp,
    strokeColor: Color = Color.Transparent,
): ShapeComponent =
    remember(
        shape,
        color,
        dynamicShader,
        margins,
        strokeWidth,
        strokeColor,
    ) {
        ShapeComponent(
            shape = shape,
            color = color.toArgb(),
            dynamicShader = dynamicShader,
            margins = margins,
            strokeWidthDp = strokeWidth.value,
            strokeColor = strokeColor.toArgb(),
        )
    }

/**
 * Creates and remembers a [ShapeComponent] with the specified properties.
 */
@Composable
public fun rememberShapeComponent(
    shape: Shape,
    color: Color = Color.Black,
    dynamicShader: DynamicShader? = null,
    margins: Dimensions = emptyDimensions(),
    strokeWidth: Dp = 0.dp,
    strokeColor: Color = Color.Transparent,
): ShapeComponent =
    rememberShapeComponent(
        shape = shape.chartShape(),
        color = color,
        dynamicShader = dynamicShader,
        margins = margins,
        strokeWidth = strokeWidth,
        strokeColor = strokeColor,
    )

/**
 * Creates and remembers a [ShapeComponent] with the specified properties.
 */
@Composable
public fun rememberShapeComponent(
    shape: ChartShape = Shapes.rectShape,
    color: Color = Color.Black,
    brush: Brush,
    margins: Dimensions = emptyDimensions(),
    strokeWidth: Dp = 0.dp,
    strokeColor: Color = Color.Transparent,
): ShapeComponent =
    rememberShapeComponent(
        shape = shape,
        color = color,
        dynamicShader = brush.toDynamicShader(),
        margins = margins,
        strokeWidth = strokeWidth,
        strokeColor = strokeColor,
    )

/**
 * Creates an [OverlayingComponent].
 *
 * @param outer the outer (background) [Component].
 * @param inner the inner (foreground) [Component].
 * @property innerPaddingStart the start padding between the inner and outer components.
 * @property innerPaddingTop the top padding between the inner and outer components.
 * @property innerPaddingEnd the end padding between the inner and outer components.
 * @property innerPaddingBottom the bottom padding between the inner and outer components.
 */
@Composable
public fun overlayingComponent(
    outer: Component,
    inner: Component,
    innerPaddingStart: Dp = 0.dp,
    innerPaddingTop: Dp = 0.dp,
    innerPaddingBottom: Dp = 0.dp,
    innerPaddingEnd: Dp = 0.dp,
): OverlayingComponent =
    remember(
        outer,
        inner,
        innerPaddingStart,
        innerPaddingTop,
        innerPaddingBottom,
        innerPaddingEnd,
    ) {
        OverlayingComponent(
            outer = outer,
            inner = inner,
            innerPaddingStartDp = innerPaddingStart.value,
            innerPaddingTopDp = innerPaddingTop.value,
            innerPaddingBottomDp = innerPaddingBottom.value,
            innerPaddingEndDp = innerPaddingEnd.value,
        )
    }

/**
 * Creates an [OverlayingComponent].
 *
 * @param outer the outer (background) [Component].
 * @param inner the inner (foreground) [Component].
 * @param innerPaddingAll the padding between the inner and outer components.
 */
@Composable
public fun overlayingComponent(
    outer: Component,
    inner: Component,
    innerPaddingAll: Dp,
): OverlayingComponent =
    overlayingComponent(
        outer = outer,
        inner = inner,
        innerPaddingStart = innerPaddingAll,
        innerPaddingTop = innerPaddingAll,
        innerPaddingBottom = innerPaddingAll,
        innerPaddingEnd = innerPaddingAll,
    )

/**
 * Creates and remembers a [TextComponent].
 *
 * @param color the text color.
 * @param textSize the text size.
 * @param background an optional [ShapeComponent] to be displayed behind the text.
 * @param ellipsize the text truncation behavior.
 * @param lineCount the line count.
 * @param padding the padding between the text and the background.
 * @param margins the margins around the background.
 * @param typeface the [Typeface] for the text.
 * @param textAlignment the text alignment.
 */
@Composable
public fun rememberTextComponent(
    color: Color = Color.Black,
    textSize: TextUnit = DefaultDimens.TEXT_COMPONENT_TEXT_SIZE.sp,
    background: ShapeComponent? = null,
    ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.END,
    lineCount: Int = DEF_LABEL_LINE_COUNT,
    padding: MutableDimensions = emptyDimensions(),
    margins: MutableDimensions = emptyDimensions(),
    typeface: Typeface? = null,
    textAlignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
): TextComponent =
    remember(
        color,
        textSize,
        background,
        ellipsize,
        lineCount,
        padding,
        margins,
        typeface,
        textAlignment
    ) {
        textComponent {
            this.color = color.toArgb()
            textSizeSp = textSize.pixelSize()
            this.ellipsize = ellipsize
            this.lineCount = lineCount
            this.background = background
            this.padding = padding
            this.margins = margins
            this.typeface = typeface
            this.textAlignment = textAlignment
        }
    }

@Composable
internal fun rememberMarker(): Marker {
    val labelBackgroundColor = MaterialTheme.colorScheme.surface
    val labelBackground =
        remember(labelBackgroundColor) {
            ShapeComponent(labelBackgroundShape, labelBackgroundColor.toArgb()).setShadow(
                radius = LABEL_BACKGROUND_SHADOW_RADIUS,
                dy = LABEL_BACKGROUND_SHADOW_DY,
                applyElevationOverlay = true,
            )
        }
    val label = rememberTextComponent(
        background = labelBackground,
        lineCount = LABEL_LINE_COUNT,
        padding = labelPadding,
        typeface = Typeface.MONOSPACE,
    )
    val indicatorInnerComponent =
        rememberShapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = rememberShapeComponent(Shapes.pillShape, Color.White)
    val indicatorOuterComponent = rememberShapeComponent(Shapes.pillShape, Color.White)
    val indicator =
        overlayingComponent(
            outer = indicatorOuterComponent,
            inner =
            overlayingComponent(
                outer = indicatorCenterComponent,
                inner = indicatorInnerComponent,
                innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue,
            ),
            innerPaddingAll = indicatorCenterAndOuterComponentPaddingValue,
        )
    val guideline =
        rememberLineComponent(
            MaterialTheme.colorScheme.onSurface.copy(GUIDELINE_ALPHA),
            guidelineThickness,
            guidelineShape,
        )

    return remember(label, indicator, guideline) {
        object : MarkerComponent(label, indicator, guideline) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                onApplyEntryColor = { entryColor ->
                    indicatorOuterComponent.color =
                        entryColor.copyColor(INDICATOR_OUTER_COMPONENT_ALPHA)
                    with(indicatorCenterComponent) {
                        color = entryColor
                        setShadow(
                            radius = INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS,
                            color = entryColor
                        )
                    }
                }
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) = with(context) {
                outInsets.top = label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                        LABEL_BACKGROUND_SHADOW_RADIUS.pixels * SHADOW_RADIUS_MULTIPLIER -
                        LABEL_BACKGROUND_SHADOW_DY.pixels
            }
        }
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
private const val LABEL_BACKGROUND_SHADOW_DY = 2f
private const val LABEL_LINE_COUNT = 1
private const val GUIDELINE_ALPHA = .2f
private const val INDICATOR_SIZE_DP = 36f
private const val INDICATOR_OUTER_COMPONENT_ALPHA = 32
private const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
private const val GUIDELINE_DASH_LENGTH_DP = 8f
private const val GUIDELINE_GAP_LENGTH_DP = 4f
private const val SHADOW_RADIUS_MULTIPLIER = 1.3f

private val labelBackgroundShape = MarkerCorneredShape(Corner.FullyRounded)
private val labelHorizontalPaddingValue = 8.dp
private val labelVerticalPaddingValue = 4.dp
private val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
private val indicatorInnerAndCenterComponentPaddingValue = 5.dp
private val indicatorCenterAndOuterComponentPaddingValue = 10.dp
private val guidelineThickness = 2.dp
private val guidelineShape =
    DashedShape(Shapes.pillShape, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)