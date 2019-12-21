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

package com.balzhoyt.cafezh.muestras

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.balzhoyt.cafezh.muestras.utils.FLAGS_FULLSCREEN
import java.io.File


const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"
private const val IMMERSIVE_FLAG_TIMEOUT = 500L
private var fotoURL: String = ""
/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class CapturarFotoActivity : AppCompatActivity() {
    private lateinit var container: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        val parametros = this.intent.extras
        if (parametros != null) {
            fotoURL = parametros.getString("urlFoto").toString()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capturar_foto)
        container = findViewById(R.id.fragment_container)
    }

    override fun onResume() {
        super.onResume()
        // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
        // be trying to set app to immersive mode before it's ready and the flags do not stick
        container.postDelayed({
            container.systemUiVisibility = FLAGS_FULLSCREEN
        }, IMMERSIVE_FLAG_TIMEOUT)
    }

    /** When key down event is triggered, relay it via local broadcast so fragments can handle it */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                val intent = Intent(KEY_EVENT_ACTION).apply { putExtra(KEY_EVENT_EXTRA, keyCode) }
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    companion object {
     /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, fotoURL).apply {
                    mkdirs()
                }
            }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }

        fun getNombreArchivo():String {
            return  when(fotoURL) {
                "Enfermedad Roya" -> {"roya_" }
                "Enfermedad Mancha de Hierro" -> {"mancha_hierro_" }
                "Enfermedad Ojo de Gallo" -> {"ojo_gallo_" }
                "Deficit de Azufre" -> {"deficit_azufre_" }
                "Deficit de Nitrogeno" -> {"deficit_nitrogeno_" }
                "Deficit de Fosforo" -> {"deficit_fosforo_" }
                "Deficit de Magnesio" -> {"deficit_magnesio_" }
                "Hojas Sanas" -> {"hojas_sanas" }
                else -> {"n"}
            }
        }
    }
}