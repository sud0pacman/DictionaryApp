package com.example.dictionary.utils

import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import java.util.Locale
import kotlin.jvm.internal.Intrinsics

//fun String.createSpannable(query: String): SpannableString {
//    val spannable = SpannableString(capitalizeFirstLetter(this))
//    val startIndex = this.indexOf(query)
//    val endIndex = startIndex + query.length
//    if (startIndex < 0 || endIndex > this.length) return spannable
//    spannable.setSpan(
//        ForegroundColorSpan(Color.RED),
//        startIndex, // start
//        endIndex, // end
//        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
//    )
//    return spannable
//}

fun String.createSpannable(query : String) : SpannableString {
    val spannableString = SpannableString(this)
    val start = this.lowercase().indexOf(query.lowercase())
    val end = start + query.length
    if(start < 0 || end > this.length) return spannableString
    spannableString.setSpan(
        ForegroundColorSpan(Color.RED),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    return spannableString
}


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

fun String.copyToClipboard(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = android.content.ClipData.newPlainText("", this)
    clipboard.setPrimaryClip(clip)
}


fun String.myLog() {
    Log.d("TTT", this)
}
