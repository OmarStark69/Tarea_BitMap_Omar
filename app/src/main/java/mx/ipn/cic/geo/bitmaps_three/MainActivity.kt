package mx.ipn.cic.geo.bitmaps_three

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.view.Menu
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
  private lateinit var buttonDrawBitmap_1: Button
  private lateinit var imageViewBitmap_1: ImageView
  private lateinit var buttonDrawBitmap_2: Button
  private lateinit var imageViewBitmap_2: ImageView
  private lateinit var buttonDrawBitmap_3: Button
  private lateinit var imageViewBitmap_3: ImageView
  private lateinit var buttonDrawBitmap_4: Button
  private lateinit var imageViewBitmap_4: ImageView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    buttonDrawBitmap_1 = findViewById(R.id.buttonDrawBitmap_1)
    imageViewBitmap_1 = findViewById(R.id.imageViewBitmap_1)
    buttonDrawBitmap_1.setOnClickListener {
      /*
        BitmapWorkerTask task = new BitmapWorkerTask(imageViewBitmap);
        task.execute(R.drawable.forest_restoration_map);
      */
      // Ejercicio. Quitar el mapa de bits.
      // Agregar 10 imágenes png de su elección (1 - 2 MB).
      // Investigar la función para generar números aleatorios.
      // Generar un número aleatorio del 1 al 9.
      // Cargar la i-esima imagen de forma aleatoria.

      //Funcion para generar un numero aleatorio
      val aleatorio = (0..10).random()
      val resourceId = resources.getIdentifier(
        "imagen_${aleatorio.toString()}", "drawable", packageName)
      if (cancelPotentialWork(resourceId, imageViewBitmap_1)) {
        Log.i("Bitmaps", "Creando objeto tarea asíncrona")
        val task = BitmapWorkerTask(imageViewBitmap_1,
          resources, imageViewBitmap_1.width, imageViewBitmap_1.height)
        val asyncDrawable = AsyncDrawable(resources, null, task)
        imageViewBitmap_1.setImageDrawable(asyncDrawable)
        task.execute(resourceId)
      }
    }
    buttonDrawBitmap_2 = findViewById(R.id.buttonDrawBitmap_2)
    imageViewBitmap_2 = findViewById(R.id.imageViewBitmap_2)
    buttonDrawBitmap_2.setOnClickListener {
      //Funcion para generar un numero aleatorio
      val aleatorio = (0..10).random()
      val resourceId = resources.getIdentifier(
        "imagen_${aleatorio.toString()}", "drawable", packageName)
      if (cancelPotentialWork(resourceId, imageViewBitmap_2)) {
        Log.i("Bitmaps", "Creando objeto tarea asíncrona")
        val task = BitmapWorkerTask(imageViewBitmap_2,
          resources, imageViewBitmap_2.width, imageViewBitmap_2.height)
        val asyncDrawable = AsyncDrawable(resources, null, task)
        imageViewBitmap_2.setImageDrawable(asyncDrawable)
        task.execute(resourceId)
      }
    }
    buttonDrawBitmap_3 = findViewById(R.id.buttonDrawBitmap_3)
    imageViewBitmap_3 = findViewById(R.id.imageViewBitmap_3)
    buttonDrawBitmap_3.setOnClickListener {
      //Funcion para generar un numero aleatorio
      val aleatorio = (0..10).random()
      val resourceId = resources.getIdentifier(
        "imagen_${aleatorio.toString()}", "drawable", packageName)
      if (cancelPotentialWork(resourceId, imageViewBitmap_3)) {
        Log.i("Bitmaps", "Creando objeto tarea asíncrona")
        val task = BitmapWorkerTask(imageViewBitmap_3,
          resources, imageViewBitmap_3.width, imageViewBitmap_3.height)
        val asyncDrawable = AsyncDrawable(resources, null, task)
        imageViewBitmap_3.setImageDrawable(asyncDrawable)
        task.execute(resourceId)
      }
    }
    buttonDrawBitmap_4 = findViewById(R.id.buttonDrawBitmap_4)
    imageViewBitmap_4 = findViewById(R.id.imageViewBitmap_4)
    buttonDrawBitmap_4.setOnClickListener {
      //Funcion para generar un numero aleatorio
      val aleatorio = (0..10).random()
      val resourceId = resources.getIdentifier(
        "imagen_${aleatorio.toString()}", "drawable", packageName)
      if (cancelPotentialWork(resourceId, imageViewBitmap_4)) {
        Log.i("Bitmaps", "Creando objeto tarea asíncrona")
        val task = BitmapWorkerTask(imageViewBitmap_4,
          resources, imageViewBitmap_4.width, imageViewBitmap_4.height)
        val asyncDrawable = AsyncDrawable(resources, null, task)
        imageViewBitmap_4.setImageDrawable(asyncDrawable)
        task.execute(resourceId)
      }
    }
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.itemMenuLoadBitmap -> {
        Log.i("Bitmaps", "Creando objeto tarea asíncrona")
        val task = BitmapWorkerTask(imageViewBitmap_1, resources,
          imageViewBitmap_1.width, imageViewBitmap_1.height)
        task.execute(R.drawable.imagen_10)
        true
      }
      R.id.itemMenuClearBitmap -> {
        // Establecer color de fondo.
        true
      }
      R.id.itemMenuExitApp -> {
        finish()
        exitProcess(RESULT_OK)
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  private fun cancelPotentialWork(data: Int, imageView: ImageView?): Boolean {
    val bitmapWorkerTask: BitmapWorkerTask? = getBitmapWorkerTask(imageView)
    if (bitmapWorkerTask != null) {
      Log.i("Bitmaps", "Buscando procesos previos...")
      val bitmapData: Int = bitmapWorkerTask.getBitmapData()
      return if (bitmapData != data) {
        // Cancel previous task
        Log.i("Bitmaps", "Cancelando tarea asíncrona...")
        bitmapWorkerTask.cancel(true)
        true
      } else {
        // The same work is already in progress
        false
      }
    }
    Log.i("Bitmaps", "No hay hilos previos o ya se finalizaron")
    // No task associated with the ImageView, or an existing task was cancelled
    return true
  }

  private fun getBitmapWorkerTask(imageView: ImageView?): BitmapWorkerTask? {
    val drawable = imageView?.drawable
    if (drawable is AsyncDrawable) {
      return drawable.bitmapWorkerTask
    }
    return null
  }
}


