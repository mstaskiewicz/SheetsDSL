package xyz.sheetsdsl

enum class BorderStyleDsl {
    DOTTED,
    DASHED,
    SOLID,
    SOLID_MEDIUM,
    SOLID_THICK,
    NONE,
    DOUBLE,
}

fun BorderStyleDsl.toModel() = name
