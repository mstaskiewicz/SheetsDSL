package xyz.sheetsdsl.examples

import com.google.api.services.sheets.v4.model.Spreadsheet

fun main() {
    val spreadsheet: Spreadsheet = spreadsheet {
        sheet {
            row {
                cell("Hello")
                cell("World!")
            }
            row(2)
            row {
                emptyCell(3)
                cell("Here!")
            }
        }
    }

    spreadsheet.openInBrowser()
}
