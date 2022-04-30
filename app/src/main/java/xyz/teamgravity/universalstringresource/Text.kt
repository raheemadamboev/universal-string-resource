package xyz.teamgravity.universalstringresource

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class Text {
    data class Dynamic(val value: String) : Text()
    class Resource(@StringRes val id: Int, vararg val args: Any) : Text()

    @Composable
    fun asString(): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> stringResource(id = id, formatArgs = args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> context.getString(id, args)
        }
    }
}