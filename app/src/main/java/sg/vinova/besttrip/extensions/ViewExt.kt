package sg.vinova.besttrip.extensions

import android.view.View

inline fun <reified T : View> T.isVisible() = visibility == View.VISIBLE