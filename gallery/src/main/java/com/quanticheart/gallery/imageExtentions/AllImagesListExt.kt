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
 *  * Copyright(c) Developed by John Alves at 2020/2/24 at 0:14:16 for quantic heart studios
 *
 */

@file:Suppress("DEPRECATION")

package com.quanticheart.gallery.imageExtentions

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import com.quanticheart.gallery.imageExtentions.extentions.getIntOrEmpty
import com.quanticheart.gallery.imageExtentions.extentions.getStringOrEmpty
import com.quanticheart.gallery.imageExtentions.model.ImageFullData

fun Activity.getAllImages(): ArrayList<ImageFullData> {
    val allImageFulls: ArrayList<ImageFullData> = ArrayList()

    val projection = getProjectionDataBaseKeys()

    //get all images from external storage
    val uriExternal: Uri? = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    uriExternal?.let { uri ->
        val cursorExt = contentResolver.query(uri, projection, null, null, null)

        cursorExt?.let { cursor ->
            while (cursor.moveToNext()) {
                allImageFulls.add(cursor.getAllDataImage(projection))
            }
        }
        cursorExt?.close()
    }

    // Get all Internal storage images
    val uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI
    uriInternal.let { uri ->
        val cursorIntr = contentResolver.query(uri, projection, null, null, null)
        cursorIntr?.let { cursor ->
            while (cursor.moveToNext()) {
                allImageFulls.add(cursor.getAllDataImage(projection))
            }
        }
        cursorIntr?.close()
    }

    allImageFulls.reverse()
    return allImageFulls
}

private fun Cursor.getAllDataImage(projection: Array<String>): ImageFullData {
    return ImageFullData(
        getIntOrEmpty(projection[0]),
        getStringOrEmpty(projection[1]),
        getStringOrEmpty(projection[2]),
        getStringOrEmpty(projection[3]),
        getStringOrEmpty(projection[4]),
        getStringOrEmpty(projection[5]),
        getStringOrEmpty(projection[6]),
        getStringOrEmpty(projection[7]),
        getStringOrEmpty(projection[8]),
        getStringOrEmpty(projection[9]),
        getStringOrEmpty(projection[10]),
        getStringOrEmpty(projection[11]),
        getStringOrEmpty(projection[12]),
        getStringOrEmpty(projection[13]),
        getStringOrEmpty(projection[14]),
        getStringOrEmpty(projection[15]),
        getStringOrEmpty(projection[16]),
        getStringOrEmpty(projection[17]),
        getStringOrEmpty(projection[18]),
        getStringOrEmpty(projection[19]),
        getStringOrEmpty(projection[20])
    )
}

@SuppressLint("InlinedApi")
private fun getProjectionDataBaseKeys(): Array<String> {
    return arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaColumns.DATA,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Images.Media.BUCKET_ID,
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.DATE_ADDED,
        MediaStore.Images.Media.DATE_MODIFIED,
        MediaStore.Images.Media.DATE_TAKEN,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.HEIGHT,
        MediaStore.Images.Media.MIME_TYPE,
        MediaStore.Images.Media.ORIENTATION,
        MediaStore.Images.Media.SIZE,
        MediaStore.Images.Media.TITLE,
        MediaStore.Images.Media.WIDTH,
        MediaStore.Images.ImageColumns.LATITUDE,
        MediaStore.Images.ImageColumns.LONGITUDE,
        MediaStore.Images.ImageColumns.DESCRIPTION,
        MediaStore.Images.ImageColumns.IS_PRIVATE,
        MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC,
        MediaStore.Images.ImageColumns.PICASA_ID
    )
}