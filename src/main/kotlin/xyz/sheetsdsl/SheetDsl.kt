package xyz.sheetsdsl

import com.google.api.services.sheets.v4.model.DimensionProperties
import com.google.api.services.sheets.v4.model.GridData
import com.google.api.services.sheets.v4.model.Sheet
import com.google.api.services.sheets.v4.model.SheetProperties
import java.lang.Integer.max

@SheetsDslMarker
class SheetDsl(private val sheetTitle: String? = null) {

    private val rows = mutableListOf<RowDsl>()
    private val columnWidths = mutableMapOf<Int, Int>()

    fun row(rowCount: Int = 1, cellCount: Int = 0, height: Int? = null, init: RowDsl.() -> Unit = {}) {
        repeat(rowCount) {
            rows += RowDsl(height).apply {
                emptyCell(cellCount)
                init()
            }
        }
    }

    fun columnWidth(columnIndexes: Iterable<Int>, widthSize: Int) {
        columnIndexes.map { columnIndex ->
            columnWidth(columnIndex, widthSize)
        }
    }

    fun columnWidth(columnIndex: Int, widthSize: Int) {
        columnWidths[columnIndex] = widthSize
    }

    fun build() = Sheet().apply {
        properties = SheetProperties().apply {
            title = sheetTitle
        }

        data = listOf(GridData().apply {
            rowData = rows.map { it.build() }

            rowMetadata = rows.map {
                DimensionProperties().apply {
                    pixelSize = it.height
                }
            }

            val columnCount = max(
                rowData.maxOfOrNull { it.values.size } ?: 0,
                (columnWidths.keys.maxOrNull() ?: -1) + 1
            )
            columnMetadata = (0 until columnCount).map {
                DimensionProperties().apply {
                    pixelSize = columnWidths[it]
                }
            }
        })
    }
}
