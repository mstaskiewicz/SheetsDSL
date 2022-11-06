package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.Color
import com.google.api.services.sheets.v4.model.ColorStyle

sealed class ColorStyleDsl

class RgbColorDsl(val color: java.awt.Color) : ColorStyleDsl()

class ThemeColorDsl(val colorType: ThemeColorTypeDsl) : ColorStyleDsl()

fun ColorStyleDsl.toModel() = ColorStyle().also {
    when(this) {
        is RgbColorDsl -> it.rgbColor = color.toModel()
        is ThemeColorDsl -> it.themeColor = colorType.toModel()
    }
}

enum class ThemeColorTypeDsl {
    TEXT,
    BACKGROUND,
    ACCENT1,
    ACCENT2,
    ACCENT3,
    ACCENT4,
    ACCENT5,
    ACCENT6,
    LINK
}

fun ThemeColorTypeDsl.toModel() = name

fun java.awt.Color.toModel() = Color().also {
    it.alpha = alpha / 255F
    it.red = red / 255F
    it.blue = blue / 255F
    it.green = green / 255F
}
