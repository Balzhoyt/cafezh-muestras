package com.balzhoyt.cafezh.muestras.ui.enviar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.balzhoyt.cafezh.muestras.R
import com.balzhoyt.cafezh.muestras.ui.gallery.UploadListAdapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList

class EnviarImagenesFragment : Fragment() {

    private lateinit var sendViewModel: EnviarImagenesViewModel
    private val RESULT_LOAD_IMAGE = 1
    private var mSelectBtn: ImageButton? = null
    private var mUploadList: RecyclerView? = null
    private lateinit var fileNameList: MutableList<String>
    private lateinit var fileDoneList: MutableList<String>
    private var uploadListAdapter: UploadListAdapter? = null
    private var mStorage: StorageReference? = null
    private lateinit var Auth:FirebaseAuth
    private var usuario = FirebaseAuth.getInstance().currentUser?.displayName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStorage = FirebaseStorage.getInstance().reference
        //showChoosingFile()
        // openGallery()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
                ViewModelProviders.of(this).get(EnviarImagenesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_enviar_imagenes, container, false)
        //val textView: TextView = root.findViewById(R.id.textView2)

        mUploadList = root.findViewById(R.id.upload_list) as RecyclerView


        fileNameList = ArrayList()
        fileDoneList = ArrayList()


        uploadListAdapter = UploadListAdapter(fileNameList ,fileDoneList)

        //RecyclerView
        mUploadList!!.setLayoutManager(LinearLayoutManager(context))
        mUploadList!!.setHasFixedSize(true)
        mUploadList!!.setAdapter(uploadListAdapter)

        root.findViewById<ImageButton>(R.id.btnAbrirGaleria).setOnClickListener {
            abrirGaleria()
        }





        sendViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        return root
    }


/** Fragment used to present the user with a gallery of photos taken */



    private fun abrirGaleria() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione las imágenes a enviar"),
                RESULT_LOAD_IMAGE
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data?.clipData != null) {
                val totalItemsSelected = data.clipData!!.itemCount
                for (i in 0 until totalItemsSelected) {
                    val fileUri = data.clipData!!.getItemAt(i).uri
                    //val nuevaimagen =redimencionarImagen(this,fileUri.toString(),400,400)
                    val fileName = getFileName(fileUri)
                    val carpeta= getCarpeta(fileName)
                    if (carpeta!="no subir"){
                        fileNameList.add(fileName)
                        fileDoneList.add("uploading")
                        uploadListAdapter!!.notifyDataSetChanged()
                        val fileToUpload = mStorage!!.child("muestras/$usuario/$carpeta").child(fileName!!)
                        fileToUpload.putFile(fileUri).addOnSuccessListener {
                            fileDoneList?.removeAt(i)
                            fileDoneList?.add(i, "done")
                            uploadListAdapter!!.notifyDataSetChanged()
                        }
                    }
                }
                //Toast.makeText(MainActivity.this, "Selected Multiple Files", Toast.LENGTH_SHORT).show();
            } else if (data?.data != null) {
                //Toast.makeText(Activity, "Selected Single File", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCarpeta(fileName: String):String {
        var carpeta="no subir"
        when {
            fileName.indexOf("roya")==0 -> {carpeta="Enfermedad roya"}
            fileName.indexOf("mancha_hierro")==0 -> {carpeta="Enfermedad mancha hierro"}
            fileName.indexOf("ojo_gallo")==0 -> {carpeta="Enfermedad ojo de gallo"}
            fileName.indexOf("deficit_azufre")==0 -> {carpeta="Deficit azufre"}
            fileName.indexOf("deficit_nitrogeno")==0 -> {carpeta="Deficit nitrogeno"}
            fileName.indexOf("deficit_fosforo")==0 -> {carpeta="Deficit fosforo"}
            fileName.indexOf("deficit_magnesio")==0 -> {carpeta="Deficit magnesio"}
            fileName.indexOf("hojas_sanas")==0 -> {carpeta="Hojas sanas"}
        }
        return carpeta
    }

    fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor =
                    context?.contentResolver?.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    fun redimencionarImagen(ctx: EnviarImagenesFragment, ruta: String, w: Int, h: Int): Drawable? { // cargamos la imagen de origen
        val BitmapOrg = BitmapFactory.decodeFile(ruta)
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
