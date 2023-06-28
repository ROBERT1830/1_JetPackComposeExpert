package com.example.composeexpert.coreUi.util

import android.content.Context

sealed class UiText {
    data class DynamicString(val text: String): UiText()
    data class StringResource(val id: Int): UiText()

    fun asString(context: Context): String =
        when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(id)
        }
}