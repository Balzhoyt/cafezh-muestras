package com.balzhoyt.cafezh.muestras.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_NAME = "com.balzhoyt.cafezh.sharedpreferences_v1"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var c_roya: Int
        get() = prefs.getInt("roya", 0)!!
        set(value) = prefs.edit().putInt("roya", value).apply()
    var c_mancha_hierro: Int
        get() = prefs.getInt("mancha_hierro", 0)!!
        set(value) = prefs.edit().putInt("mancha_hierro", value).apply()
    var c_ojo_gallo: Int
        get() = prefs.getInt("ojo_gallo", 0)!!
        set(value) = prefs.edit().putInt("ojo_gallo", value).apply()
    var c_deficit_azufre: Int
        get() = prefs.getInt("deficit_azufre", 0)!!
        set(value) = prefs.edit().putInt("deficit_azufre", value).apply()
    var c_deficit_nitrogeno: Int
        get() = prefs.getInt("deficit_nitrogeno", 0)!!
        set(value) = prefs.edit().putInt("deficit_nitrogeno", value).apply()
    var c_deficit_fosforo: Int
        get() = prefs.getInt("deficit_fosforo", 0)!!
        set(value) = prefs.edit().putInt("deficit_fosforo", value).apply()
    var c_deficit_magnesio: Int
        get() = prefs.getInt("deficit_magnesio", 0)!!
        set(value) = prefs.edit().putInt("deficit_magnesio", value).apply()
    var c_hojas_sanas: Int
        get() = prefs.getInt("hojas_sanas", 0)!!
        set(value) = prefs.edit().putInt("hojas_sanas", value).apply()


}