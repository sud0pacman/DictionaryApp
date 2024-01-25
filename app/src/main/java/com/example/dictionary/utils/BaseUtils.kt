package com.example.dictionary.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlin.jvm.internal.Intrinsics


/* compiled from: BaseUtils.kt */
class BaseUtils private constructor() {

    fun capitalizeFirstLetter(str: String): String {
        Intrinsics.checkNotNullParameter(str, "<this>")
        if (str.isEmpty()) {
            return str
        }
        val substring = str.substring(0, 1)
        Intrinsics.checkNotNullExpressionValue(
            substring,
            "this as java.lang.String…ing(startIndex, endIndex)"
        )
        val upperCase = substring.uppercase()
        Intrinsics.checkNotNullExpressionValue(
            upperCase,
            "this as java.lang.String).toUpperCase(Locale.ROOT)"
        )
        val substring2 = str.substring(1)
        Intrinsics.checkNotNullExpressionValue(
            substring2,
            "this as java.lang.String).substring(startIndex)"
        )
        return upperCase + substring2
    }


}
