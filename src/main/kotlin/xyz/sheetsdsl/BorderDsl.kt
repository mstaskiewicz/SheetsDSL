package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.Border
import com.google.api.services.sheets.v4.model.Borders
import xyz.sheetsdsl.BorderSideDsl.*

@SheetsDslMarker
data class BorderDsl(
    var colorStyle: ColorStyleDsl? = null,
    var style: BorderStyleDsl? = null,
)

fun BorderDsl.toModel(): Border? = Border().also {
    it.colorStyle = colorStyle?.toModel()
    it.style = style?.toModel()
}.takeIf {
    it.style != null
}

fun Map<BorderSideDsl, BorderDsl>.toModel(): Borders = Borders().also {
    it.bottom = get(BOTTOM)?.toModel()
    it.left = get(LEFT)?.toModel()
    it.right = get(RIGHT)?.toModel()
    it.top = get(TOP)?.toModel()
}
