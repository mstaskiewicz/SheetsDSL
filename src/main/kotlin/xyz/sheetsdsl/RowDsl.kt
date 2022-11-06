package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.RowData

@SheetsDslMarker
class RowDsl(val height: Int? = null) {

    private val cells = mutableListOf<CellDsl>()

    fun cell(value: String = "", init: CellDsl.() -> Unit = {}) =
        CellDsl(value).apply(init).also { cells += it }

    fun cell(value: Number, init: CellDsl.() -> Unit = {}) =
        CellDsl(value).apply(init).also { cells += it }

    fun cellFormula(formula: String, init: CellDsl.() -> Unit = {}) =
        CellDsl(CellFormulaDsl(formula)).apply(init).also { cells += it }

    fun emptyCell(count: Int = 1) {
        if (count < 1) return
        repeat(count) { cell() }
    }

    operator fun String.unaryPlus() = cell(this)

    internal fun build() = RowData().apply {
        setValues(cells.map { it.build() })
    }
}
