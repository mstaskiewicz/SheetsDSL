package xyz.sheetsdsl

enum class VerticalAlignmentDsl {
    TOP, MIDDLE, BOTTOM
}

fun VerticalAlignmentDsl.toModel() = name
