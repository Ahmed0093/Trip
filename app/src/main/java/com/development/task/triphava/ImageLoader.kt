package com.development.task.triphava

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

val imageLoader by lazy { ImageLoader() }

class ImageLoader {

    fun providesGlide(): RequestManager {
        return Glide.with(DomainIntegration.getContext())
    }

}