package top.cuggis.schooldan.ext

import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat

fun Drawable.changeToColor(color:Int): Drawable {
    val tempDrawable= DrawableCompat.wrap(this)
    DrawableCompat.setTint(tempDrawable,color)
    return tempDrawable
}

