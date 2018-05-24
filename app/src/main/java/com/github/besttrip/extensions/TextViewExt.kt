package com.github.besttrip.extensions

import android.graphics.Color
import android.widget.TextView

fun TextView?.enableClick(enable: Boolean) {
    if (this == null) return
    isClickable = enable
    setTextColor(if (enable) Color.WHITE else Color.DKGRAY)
}