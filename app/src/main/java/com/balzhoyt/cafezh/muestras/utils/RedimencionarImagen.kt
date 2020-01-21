package com.balzhoyt.cafezh.muestras.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable


class RedimencionarImagen {
fun resizeImage(ctx: Context, resId: Int, w: Int, h: Int): Drawable? { // cargamos la imagen de origen
    val BitmapOrg = BitmapFactory.decodeResource(ctx.getResources(),resId)
    val width = BitmapOrg.width
    val height = BitmapOrg.height
    // calculamos el escalado de la imagen destino
    val scaleWidth = w.toFloat() / width
    val scaleHeight = h.toFloat() / height
    // para poder manipular la imagen
// debemos crear una matriz
    val matrix = Matrix()
    // resize the Bitmap
    matrix.postScale(scaleWidth, scaleHeight)
    // volvemos a crear la imagen con los nuevos valores
    val resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0,
            width, height, matrix, true)
    // si queremos poder mostrar nuestra imagen tenemos que crear un
// objeto drawable y así asignarlo a un botón, imageview...
    return BitmapDrawable(resizedBitmap)
}
}