package mx.ipn.cic.geo.bitmaps_three

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.lang.ref.WeakReference
import kotlin.math.max
import kotlin.math.roundToInt

class BitmapWorkerTask(imageView: ImageView, resources: Resources, width: Int, height: Int) :
  AsyncTask<Int?, Void?, Bitmap?>() {

  // protected Bitmap doInBackground(Integer... params) {
  // protected void onPostExecute(Bitmap bitmap) {

  private val imageViewReference: WeakReference<ImageView?> = WeakReference(imageView)
  private val resourcesReference: WeakReference<Resources?> = WeakReference(resources)
  private var data: Int = 0

  fun getBitmapData(): Int {
    return data
  }

  // Decode image in background.
  override fun doInBackground(vararg params: Int?): Bitmap? {
    val imageView: ImageView? = imageViewReference.get()

    Log.d("Bitmaps", "Cargando imagen...")
    return try {
      data = params[0]!!
      decodeSampledBitmapFromResource(
        imageView!!.context.resources, data,
        imageView.width, imageView.height
      )
    } catch (e: Exception) {
      null
    }
  }

  // Once complete, see if ImageView is still around and set bitmap.
  override fun onPostExecute(bitmap: Bitmap?) {
    val imageView: ImageView? = imageViewReference.get()
    val bitmapWorkerTask: BitmapWorkerTask? = getBitmapWorkerTask(imageView!!)

    if (isCancelled) return

    Log.d("Bitmaps", "Mostrando imagen...")
    try {
      imageView.setImageBitmap(bitmap!!)
    } catch (e: Exception) {

    }
  }

  private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

      // Calculate ratios of height and width to requested height and width
      val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
      val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()

      // Choose the smallest ratio as inSampleSize value, this will guarantee
      // a final image with both dimensions larger than or equal to the
      // requested height and width.
      inSampleSize = max(heightRatio, widthRatio)
    }
    return inSampleSize
  }

  private fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {

    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, resId, options)

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(res, resId, options)
  }

  private fun getBitmapWorkerTask(imageView: ImageView?): BitmapWorkerTask? {
    val drawable = imageView?.drawable

    return if (drawable is AsyncDrawable) {
      drawable.bitmapWorkerTask
    } else {
      null
    }
  }
}