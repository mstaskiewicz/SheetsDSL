package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.api.services.sheets.v4.model.SpreadsheetProperties


@SheetsDslMarker
class SpreadsheetDsl(private val spreadsheetTitle: String? = null) {

    private val sheetList = mutableListOf<SheetDsl>()

    fun sheet(title: String? = null, init: SheetDsl.() -> Unit = {}) {
        sheetList += SheetDsl(title).apply(init)
    }

    internal fun build() = Spreadsheet().apply {
        properties = SpreadsheetProperties().apply {
            title = spreadsheetTitle
        }
        sheets = sheetList.map { it.build() }
    }
}
