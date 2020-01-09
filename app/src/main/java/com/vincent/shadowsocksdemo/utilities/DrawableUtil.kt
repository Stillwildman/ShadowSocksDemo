package com.vincent.shadowsocksdemo.utilities

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import com.vincent.shadowsocksdemo.AppController.Companion.instance

/**
 * For the compatible methods that creating of Drawables.
 *
 * @author Vincent Chang (2018/1/10)
 */
object DrawableUtil {

    fun getTintDrawable(drawable: Drawable, colorRes: Int): Drawable {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        val myCanvas = Canvas(bitmap)

        val color = ContextCompat.getColor(instance.applicationContext, colorRes)

        val filter: ColorFilter = if (Build.VERSION.SDK_INT >= 29) {
            BlendModeColorFilter(color, BlendMode.SRC_OVER)
        }
        else {
            PorterDuffColorFilter(color, PorterDuff.Mode.SRC_OVER)
        }

        drawable.colorFilter = filter

        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(myCanvas)

        return BitmapDrawable(instance.resources, bitmap)
    }

    fun getVectorDrawable(drawableRes: Int): Drawable? {
        return ContextCompat.getDrawable(instance.applicationContext, drawableRes)
    }
}