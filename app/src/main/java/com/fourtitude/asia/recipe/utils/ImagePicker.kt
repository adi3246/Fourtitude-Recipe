package com.fourtitude.asia.recipe.utils

import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import gun0912.tedbottompicker.TedBottomPicker

class ImagePicker {

    companion object {
        val instance: ImagePicker by lazy { Holder.INSTANCE }

        private object Holder { val INSTANCE: ImagePicker
            get() = ImagePicker()
        }
    }

    fun open(activity: AppCompatActivity, isSinglePick: Boolean, listener: OnImagePickerListener){

        var selectedUriList: List<Uri>? = null

        if(isSinglePick){
            /*TedBottomPicker.with(activity)
                .show {
                    val imageList: ArrayList<String> = ArrayList()

                    imageList.add(it.toString())

                    if(imageList.size>0)
                        listener.imageList = imageList
                }*/

            TedBottomPicker.with(activity)
                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                //.setSelectedUri(selectedUri)
                //.showVideoMedia()
                //.setPeekHeight(1200)
                .show { uri ->
                    val imageList: ArrayList<String> = ArrayList()

                    imageList.add(uri.toString())

                    if(imageList.size>0)
                        listener.imageList = imageList
                }
        }else{
            /*TedBottomPicker.with(activity)
                .setPeekHeight(activity.resources.displayMetrics.heightPixels/2)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .setPreviewMaxCount(50)
                .setSelectedUriList(selectedUriList)
                .showMultiImage {
                    val imageList: ArrayList<String> = ArrayList()

                    for (item in it){
                        Log.d("Image path gagaga: ", item.toString())
                        imageList.add(item.toString())
                    }

                    if(imageList.size>0)
                        listener.imageList = imageList
                }*/

            TedBottomPicker.with(activity)
                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                //.setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .setSelectedUriList(selectedUriList)
                .showMultiImage { uriList ->
                    val imageList: ArrayList<String> = ArrayList()

                    for (item in uriList){
                        Log.d("Image path gagaga: ", item.toString())
                        imageList.add(item.toString())
                    }

                    if(imageList.size>0)
                        listener.imageList = imageList
                }
        }
    }

    interface OnImagePickerListener {
        var imageList: ArrayList<String>
    }
}