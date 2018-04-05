package sg.vinova.besttrip.extensions

import android.util.Patterns

inline fun <reified T : String?> T.isNotNullAndIsEmpty() = this != null && this.isEmpty()

inline fun <reified T : String?> T.isNotNull() = this != null

inline fun <reified T : String?> T.invalidEmail() = !Patterns.EMAIL_ADDRESS.matcher(this).matches()