package xyz.sheetsdsl

enum class BorderSideDsl(private vararg val contains: BorderSideDsl) {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT,
    TOP_BOTTOM(TOP, BOTTOM),
    LEFT_RIGHT(LEFT, RIGHT),
    ALL(TOP, RIGHT, BOTTOM, LEFT);

    operator fun contains(side: BorderSideDsl) = side == this || side in contains
}
