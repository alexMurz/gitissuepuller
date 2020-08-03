package com.example.gitissuepull

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("manager")
fun setManager(view: RecyclerView, manager: RecyclerView.LayoutManager) {
    view.layoutManager = manager
}

@BindingAdapter("fromUri")
fun setImageViewFromUri(view: ImageView, uri: String) {
    Glide
        .with(view)
        .asBitmap()
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .into(view)
}
