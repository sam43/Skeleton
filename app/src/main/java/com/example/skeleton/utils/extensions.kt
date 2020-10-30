package com.example.skeleton.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.skeleton.R

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(
            this,
            ViewModelFactory(creator)
        ).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(
            this,
            ViewModelFactory(creator)
        ).get(T::class.java)
}

fun Context.loadUserAvatar(url: String?, holder: ImageView) {
    if (url != null) {
        Glide.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .apply(
                RequestOptions.placeholderOf(0)
                    .error(R.drawable.ic_launcher_background)
                    .dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
            )
            .into(holder)
    } else {
        holder.setImageResource(R.drawable.ic_launcher_background)
    }
}

fun Context.pop(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

inline fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}