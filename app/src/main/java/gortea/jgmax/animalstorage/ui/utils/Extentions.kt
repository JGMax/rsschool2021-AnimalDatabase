package gortea.jgmax.animalstorage.ui.utils

import android.content.Context
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import java.lang.ClassCastException
import kotlin.math.roundToInt

fun EditText.getString(): String? {
    return this.text?.toString()
}

fun Any?.getMessageString(context: Context): String {
    return when (this) {
        is Int -> context.getString(this)
        is String -> this
        else -> throw ClassCastException("Unknown message type")
    }
}

fun Int.toPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * (metrics.density)).roundToInt()
}

fun RecyclerView.Adapter<*>.hasPrevious(position: Int) = position > 0
fun RecyclerView.Adapter<*>.hasNext(position: Int) = position < itemCount - 1