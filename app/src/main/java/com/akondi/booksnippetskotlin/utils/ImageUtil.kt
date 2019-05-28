package com.akondi.booksnippetskotlin.utils

import android.net.Uri
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

public class ImageUtil {

    public fun reduceImageSize(file_name: String, size: Int): AbstractDraweeController<*, *> {
        //ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(file_name))
        val request = ImageRequestBuilder.newBuilderWithSource(getPackageLabelUri(file_name))
            .setResizeOptions(ResizeOptions(size, size))
            .build()
        return Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setTapToRetryEnabled(true)
            .build()
    }

    //API_URL +  GET_LABEL_URL + file_name
    private fun getPackageLabelUri(file_name: String): Uri {
        return Uri.parse(file_name)
    }
}
