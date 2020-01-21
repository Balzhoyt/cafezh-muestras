package com.balzhoyt.cafezh.muestras.ui.gallery

/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */




import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balzhoyt.cafezh.muestras.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList


/** Fragment used to present the user with a gallery of photos taken */
class GalleryFragmentMnu :Fragment() {
    private lateinit var shareViewModel: GalleryViewModel


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

        shareViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery_mnu, container, false)

        //val textView: TextView = root.findViewById(R.id.text_share)

        //mSelectBtn = root.findViewById(R.id.select_btn) as ImageButton
        mUploadList = root.findViewById(R.id.upload_list) as RecyclerView


        fileNameList = ArrayList()
        fileDoneList = ArrayList()

        uploadListAdapter = UploadListAdapter(fileNameList ,fileDoneList)

        //RecyclerView
        mUploadList!!.setLayoutManager(LinearLayoutManager(context))
        mUploadList!!.setHasFixedSize(true)
        mUploadList!!.setAdapter(uploadListAdapter)

        /**root.findViewById<Button>(R.id.select_btn).setOnClickListener {
            //    openGallery()
            showChoosingFile()

        }
        mSelectBtn!!.setOnClickListener{
            showChoosingFile()
        }
        */

        abrirGaleria()

        shareViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        return root
    }


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

}