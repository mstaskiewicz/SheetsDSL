package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.CellData
import com.google.api.services.sheets.v4.model.CellFormat
import com.google.api.services.sheets.v4.model.ExtendedValue
import com.google.api.services.sheets.v4.model.TextRotation
import xyz.sheetsdsl.BorderSideDsl.*
import xyz.sheetsdsl.BorderStyleDsl.SOLID
import java.awt.Color


@SheetsDslMarker
class CellDsl(
    private val value: Any? = null,
    var verticalAlignment: VerticalAlignmentDsl? = null,
    var horizontalAlignment: HorizontalAlignmentDsl? = null,
    var backgroundColor: Color? = null,
    var rotation: Int? = null,
) {

    private var sideToBorderMap: Map<BorderSideDsl, BorderDsl> = listOf(TOP, RIGHT, BOTTOM, LEFT)
        .associateWith { BorderDsl() }

    private var textFormat: TextFormatDsl? = null

    fun border(
        style: BorderStyleDsl? = SOLID,
        color: Color? = null,
        sides: BorderSideDsl = ALL,
    ) {
        sideToBorderMap
            .filterKeys { side -> side in sides }
            .values.forEach { border ->
                color?.also { border.colorStyle = RgbColorDsl(color) }
                style?.also { border.style = style }
            }
    }

    fun font(init: TextFormatDsl.() -> Unit) {
        textFormat = TextFormatDsl().apply(init)
    }

    internal fun build() = CellData().apply {
        userEnteredFormat = CellFormat().also {
            it.backgroundColorStyle = backgroundColor?.run { RgbColorDsl(this) }?.toModel()
            it.borders = sideToBorderMap.toModel()
            it.horizontalAlignment = horizontalAlignment?.toModel()
            it.verticalAlignment = verticalAlignment?.toModel()
            it.textFormat = textFormat?.toModel()
            it.textRotation = rotation?.let { TextRotation().apply { angle = it.coerceIn(-90, 90) } }
        }

        userEnteredValue = when (value) {
            null, "" -> null
            is Boolean ->
                ExtendedValue().setBoolValue(value)

            is Number ->
                ExtendedValue().setNumberValue(value.toDouble())

            is CellFormulaDsl ->
                ExtendedValue().setFormulaValue("=" + value.formula.substringAfter("="))

            else ->
                ExtendedValue().setStringValue(value.toString())
        }
    }
}

operator fun CellDsl.rem(angle: Int) = apply {
    rotation = angle
}

infix fun CellDsl.bg(color: Color) = apply {
    backgroundColor = color
}

infix fun CellDsl.align(horizontalAlignment: HorizontalAlignmentDsl) = also {
    it.horizontalAlignment = horizontalAlignment
}

infix fun CellDsl.vAlign(verticalAlignment: VerticalAlignmentDsl) = also {
    it.verticalAlignment = verticalAlignment
}
