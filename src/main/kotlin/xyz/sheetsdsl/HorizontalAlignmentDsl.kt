package xyz.sheetsdsl

enum class HorizontalAlignmentDsl {
    LEFT, CENTER, RIGHT
}

fun HorizontalAlignmentDsl.toModel() = name
