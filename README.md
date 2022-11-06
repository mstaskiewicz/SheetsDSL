# SheetsDSL - Kotlin DSL for Google Sheets

The project is heavily inspired by [ExcelDSL](https://github.com/aPureBase/ExcelDSL)

## Examples

### Simple
Analogous to the example from [ExcelDSL](https://github.com/aPureBase/ExcelDSL).

```kotlin
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
```

### Complex - [_online result_](https://docs.google.com/spreadsheets/d/1rra9kUXFBo1kBaJ8mNunZJVIT9lONOooBYjEHuEluWY)

```kotlin
fun main() {
    val spreadsheet = spreadsheet("Generated Spreadsheet") {
        sheet("First") {
            row {
                cell("Bold Roboto") {
                    font {
                        fontFamily = "Roboto"
                        bold = true
                    }
                }

                cell("White on red and aligned to right") {
                    font {
                        color = WHITE
                    }
                } bg RED align RIGHT

                +"Am I rotated?" % 45
            }

            columnWidth(1, 240)

            row(cellCount = 2) {
                cell("4 * 84 =") {
                    horizontalAlignment = RIGHT
                }
                cellFormula("= 4 * 84") {
                    backgroundColor = GRAY
                }
            }

            row()

            row {
                emptyCell()
                cell(4234.234) {
                    border(DOTTED, BLUE, TOP_BOTTOM)
                }
            }
        }

        sheet("second - empty")

        sheet {
            columnWidth(0..2, 150)
            columnWidth(3, 60)

            row(cellCount = 3) {
                cell("thin") {
                    font {
                        bold = true
                    }
                    horizontalAlignment = CENTER
                }
            }

            row(2)

            row(height = 63) {
                cell("tall") {
                    font {
                        bold = true
                    }
                } align CENTER vAlign MIDDLE
            }
        }
    }

    spreadsheet.openInBrowser()
}
```

### Running

It requires a configured [Google Cloud](https://console.cloud.google.com/)
project with enabled
[Sheets API](https://console.cloud.google.com/apis/api/sheets.googleapis.com)
and valid credentials placed at the project's relative path: 
`src/main/resources/credentials.json`.

A comprehensive guide can be found on
[Google Developers pages](https://developers.google.com/sheets/api/quickstart/java).
