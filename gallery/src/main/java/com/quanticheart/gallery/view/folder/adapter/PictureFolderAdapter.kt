/*
 *
 *  *                                     /@
 *  *                      __        __   /\/
 *  *                     /==\      /  \_/\/
 *  *                   /======\    \/\__ \__
 *  *                 /==/\  /\==\    /\_|__ \
 *  *              /==/    ||    \=\ / / / /_/
 *  *            /=/    /\ || /\   \=\/ /
 *  *         /===/   /   \||/   \   \===\
 *  *       /===/   /_________________ \===\
 *  *    /====/   / |                /  \====\
 *  *  /====/   /   |  _________    /      \===\
 *  *  /==/   /     | /   /  \ / / /         /===/
 *  * |===| /       |/   /____/ / /         /===/
 *  *  \==\             /\   / / /          /===/
 *  *  \===\__    \    /  \ / / /   /      /===/   ____                    __  _         __  __                __
 *  *    \==\ \    \\ /____/   /_\ //     /===/   / __ \__  ______  ____ _/ /_(_)____   / / / /__  ____ ______/ /_
 *  *    \===\ \   \\\\\\\/   ///////     /===/  / / / / / / / __ \/ __ `/ __/ / ___/  / /_/ / _ \/ __ `/ ___/ __/
 *  *      \==\/     \\\\/ / //////       /==/  / /_/ / /_/ / / / / /_/ / /_/ / /__   / __  /  __/ /_/ / /  / /_
 *  *      \==\     _ \\/ / /////        |==/   \___\_\__,_/_/ /_/\__,_/\__/_/\___/  /_/ /_/\___/\__,_/_/   \__/
 *  *        \==\  / \ / / ///          /===/
 *  *        \==\ /   / / /________/    /==/
 *  *          \==\  /               | /==/
 *  *          \=\  /________________|/=/
 *  *            \==\     _____     /==/
 *  *           / \===\   \   /   /===/
 *  *          / / /\===\  \_/  /===/
 *  *         / / /   \====\ /====/
 *  *        / / /      \===|===/
 *  *        |/_/         \===/
 *  *                       =
 *  *
 *  * Copyright(c) Developed by John Alves at 2020/2/23 at 0:55:2 for quantic heart studios
 *
 */
package com.quanticheart.gallery.view.folder.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.quanticheart.gallery.R
import com.quanticheart.gallery.extentions.createSelectDialog
import com.quanticheart.gallery.view.folder.adapter.PictureFolderAdapter.FolderHolder
import com.quanticheart.gallery.view.folder.constants.FolderConstants
import com.quanticheart.gallery.view.folder.model.ImageFolderData
import com.quanticheart.gallery.view.images.view.indicator.ImageIndicatorActivity
import com.quanticheart.gallery.view.images.view.list.ImageListActivity
import kotlinx.android.synthetic.main.item_picture_folder.view.*

class PictureFolderAdapter(recyclerView: RecyclerView) : RecyclerView.Adapter<FolderHolder>() {

    private val database = ArrayList<ImageFolderData>()

    init {
        recyclerView.apply {
            adapter = this@PictureFolderAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder =
        FolderHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_picture_folder,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        holder.bind(database[position])
    }

    override fun getItemCount(): Int = database.size

    inner class FolderHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(folder: ImageFolderData) {
            Glide.with(itemView.context)
                .load(folder.firstPic)
                .apply(RequestOptions().centerCrop())
                .into(itemView.folderPic)

            itemView.folderName.text = "(${folder.numberOfPics}) ${folder.folderName}"
            itemView.folderPic.setOnClickListener {
                itemView.context.createSelectDialog(
                    "Select type view",
                    "Two views avaliable",
                    "simple",
                    {
                        val move = Intent(itemView.context, ImageListActivity::class.java)
                        move.putExtra(FolderConstants.FolderDataKey, folder)
                        itemView.context.startActivity(move)
                    },
                    "indicator",
                    {
                        val move = Intent(itemView.context, ImageIndicatorActivity::class.java)
                        move.putExtra(FolderConstants.FolderDataKey, folder)
                        itemView.context.startActivity(move)
                    }
                )
            }
        }
    }

    fun addData(list: ArrayList<ImageFolderData>) {
        if (list.isNotEmpty()) {
            database.clear()
            database.addAll(list)
            notifyDataSetChanged()
        }
    }
}