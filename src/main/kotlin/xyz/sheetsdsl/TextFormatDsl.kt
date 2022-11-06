package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.TextFormat
import java.awt.Color

@SheetsDslMarker
data class TextFormatDsl(
    var color: Color? = null,
    var fontFamily: String? = null,
    var fontSize: Int? = null,
    var bold: Boolean? = null,
    var italic: Boolean? = null,
    var strikethrough: Boolean? = null,
    var underline: Boolean? = null,
)

fun TextFormatDsl.toModel() = TextFormat().also {
    it.foregroundColorStyle = color?.run { RgbColorDsl(this) }?.toModel()
    it.fontFamily = fontFamily
    it.fontSize = fontSize
    it.bold = bold
    it.italic = italic
    it.strikethrough = strikethrough
    it.underline = underline
}
