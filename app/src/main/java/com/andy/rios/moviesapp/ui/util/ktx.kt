package com.andy.rios.moviesapp.ui.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiffCallback<K>(val compareItems: (old: K, new: K) -> Boolean, val compareContents: (old: K, new: K) -> Boolean) : DiffUtil.ItemCallback<K>() {
    override fun areItemsTheSame(old: K, new: K) = compareItems(old, new)
    override fun areContentsTheSame(old: K, new: K) = compareContents(old, new)
}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            block()
        }
    }
}

fun simpleDateFormat(date: String, format: String = "yyyy-MM-dd"): Date {
    return SimpleDateFormat(format, Locale.getDefault()).parse(date)!!
}


fun EditText.dismissKeyboard() {
    val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}