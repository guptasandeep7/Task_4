package com.example.task_4.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("floatToString")
fun TextView.floatToString(value: Float) {
    this.text = value.toInt().toString()
}