package com.fourtitude.asia.recipe.utils.imageCompressor

class ImageResultModel(imagePath: String, base64Image: String){

    var imagePath: String = ""

    var base64Image: String = ""

    init {
        this.imagePath = imagePath

        this.base64Image = base64Image
    }
}