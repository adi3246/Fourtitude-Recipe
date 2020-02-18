package com.fourtitude.asia.recipe.utils.imageCompressor

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import java.io.File
import java.io.IOException
import java.util.*

class ImageCompressTask : Runnable {

    private var mContext: Context? = null
    private var originalPaths = ArrayList<String>()
    private val mHandler = Handler(Looper.getMainLooper())
    private val result = ArrayList<File>()
    //private val result = ArrayList<ItemImageModel>()
    private var mIImageCompressTaskListener: ImageCompressTaskListener? = null


    constructor(context: Context, path: String, compressTaskListener: ImageCompressTaskListener) {

        Uri.parse(path).path?.let { originalPaths.add(it) }
        mContext = context

        mIImageCompressTaskListener = compressTaskListener
    }

    constructor(context: Context, paths: ArrayList<String>, compressTaskListener: ImageCompressTaskListener) {
        for (path in paths) {
            Uri.parse(path).path?.let { originalPaths.add(it) }
        }

        mContext = context
        mIImageCompressTaskListener = compressTaskListener
    }

    override fun run() {

        try {

            //Loop through all the given paths and collect the compressed file from Util.getCompressed(Context, String)
            for (path in originalPaths) {
                val file = ImageCompressorUtil.getCompressed(mContext, path)
                //add it!
                result.add(file)
            }
            //use Handler to post the result back to the main Thread
            mHandler.post {
                if (mIImageCompressTaskListener != null)
                    mIImageCompressTaskListener!!.onComplete(result)
            }
        } catch (ex: IOException) {
            //There was an error, report the error back through the callback
            mHandler.post {
                if (mIImageCompressTaskListener != null)
                    mIImageCompressTaskListener!!.onError(ex)
            }
        }

    }
}
