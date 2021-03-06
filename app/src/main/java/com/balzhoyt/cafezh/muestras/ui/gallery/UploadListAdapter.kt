package com.balzhoyt.cafezh.muestras.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balzhoyt.cafezh.muestras.R


class UploadListAdapter(
    var fileNameList: MutableList<String>,
    var fileDoneList: MutableList<String>
) :
    RecyclerView.Adapter<UploadListAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_single, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileName = fileNameList[position]
        holder.fileNameView.text = fileName
        val fileDone = fileDoneList[position]
        if (fileDone == "uploading") {
            holder.fileDoneView.setImageResource(R.mipmap.progress)
        } else {
            holder.fileDoneView.setImageResource(R.mipmap.checked)
        }
    }

    override fun getItemCount(): Int {
        return fileNameList.size
    }

    inner class ViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {
        var fileNameView: TextView
        var fileDoneView: ImageView

        init {
            fileNameView = mView.findViewById<View>(R.id.upload_filename) as TextView
            fileDoneView =  mView.findViewById<View>(R.id.upload_loading) as ImageView
        }
    }

}
