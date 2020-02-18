package com.fourtitude.asia.recipe.utils.imageCompressor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageCompressorUtil {

    //SDF to generate a unique name for our compress file.
    val SDF = SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault())
    private var random = Random()

    /*
        compress the file/photo from @param <b>path</b> to a private location on the current device and return the compressed file.
        @param path = The original image path
        @param context = Current android Context
     */
    @Throws(IOException::class)
    fun getCompressed(context: Context?, path: String): File {

        if (context == null)
            throw NullPointerException("Context must not be null.")
        //getting device external cache directory, might not be available on some devices,
        // so our code fall back to internal storage cache directory, which is always available but in smaller quantity
        var cacheDir = context.externalCacheDir
        if (cacheDir == null)
        //fall back
            cacheDir = context.cacheDir

        val rootDir = cacheDir!!.absolutePath + "/ImageCompressor"
        val root = File(rootDir)

        //Create ImageCompressor folder if it doesnt already exists.
        if (!root.exists())
            root.mkdirs()

        //decode and resize the original bitmap from @param path.
        val bitmap = decodeImageFromFiles(path, /* your desired width*/300, /*your desired height*/ 300)

        //create placeholder for the compressed image file
        val compressed = File(root, (random.nextInt(9999 - 1000) + 1000).toString() + ".png" /*Your desired format*/)

        //convert the decoded bitmap to stream
        val byteArrayOutputStream = ByteArrayOutputStream()

        /*compress bitmap into byteArrayOutputStream
            Bitmap.compress(Format, Quality, OutputStream)

            Where Quality ranges from 1 - 100.
         */
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)


        /*
        Right now, we have our bitmap inside byteArrayOutputStream Object, all we need next is to write it to the compressed file we created earlier,
        java.io.FileOutputStream can help us do just That!

         */
        val fileOutputStream = FileOutputStream(compressed)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.flush()

        fileOutputStream.close()

        //File written, return to the caller. Done!
        return compressed

        //return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
    }

    fun decodeImageFromFiles(path: String, width: Int, height: Int): Bitmap {
        val scaleOptions = BitmapFactory.Options()
        scaleOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, scaleOptions)
        var scale = 1
        while (scaleOptions.outWidth / scale / 2 >= width && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2
        }
        // decode with the sample size
        val outOptions = BitmapFactory.Options()
        outOptions.inSampleSize = scale

        val bitmap = BitmapFactory.decodeFile(path, outOptions)

        return when (ExifInterface(File(Uri.parse(Uri.decode(path)).path)).getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
            ExifInterface.ORIENTATION_ROTATE_90 ->
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply {
                    postRotate(90F) }, true)
            ExifInterface.ORIENTATION_ROTATE_180 ->
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply {
                    postRotate(180F) }, true)
            ExifInterface.ORIENTATION_ROTATE_270 ->
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply {
                    postRotate(270F) }, true)
            else -> bitmap
        }
    }
}