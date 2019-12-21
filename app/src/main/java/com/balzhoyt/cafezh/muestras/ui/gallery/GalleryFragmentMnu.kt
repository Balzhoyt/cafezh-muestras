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




import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.balzhoyt.cafezh.muestras.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


var imageView: ImageView? = null
var btnCargarFoto: Button? = null
private const val PICK_IMAGE = 100
var imageUri: Uri? = null
private val CHOOSING_IMAGE_REQUEST = 1234


/** Fragment used to present the user with a gallery of photos taken */
class GalleryFragmentMnu :Fragment() {
    private lateinit var shareViewModel: GalleryViewModel
    lateinit var storage: FirebaseStorage
    lateinit var filePath2:StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = FirebaseStorage.getInstance()
        showChoosingFile()
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

        imageView = root.findViewById(R.id.ivfoto)
        imageView?.setOnClickListener { openGallery() }

        root.findViewById<Button>(R.id.btnCargarFoto).setOnClickListener {
            //    openGallery()
            showChoosingFile()
        }



        shareViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        return root
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }
    private fun showChoosingFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "seleccione una imagen"), CHOOSING_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == CHOOSING_IMAGE_REQUEST) {
            val storageRef = storage.reference

            if (data != null) {
                imageUri = data.data

                //Codigo para subir la imagen a Firebase
                val nombreFoto=imageUri?.lastPathSegment
                filePath2 = storageRef.child("roya").child(nombreFoto.toString())
                imageUri?.let {
                    filePath2.putFile(it).addOnSuccessListener {
                        //Toast.makeText(this@GalleryFragmentMnu, "Se ha subido la foto a FireBase", Toast.LENGTH_SHORT).show() } }


                    }
                    imageView?.setImageURI(imageUri)
                }
            }

        }


    }
}