package com.example.baseproject.data

import android.util.Log
import java.io.File


class DataRepositoryImpl : DataRepository {
    override fun getData(file: File): List<File> {
        val musicFile = mutableListOf<File>()
        file.listFiles()?.let {
            for (currentFile in it){
                Log.d("file",currentFile.name.toString())
                if (currentFile.isDirectory && !currentFile.isHidden){
                    musicFile.addAll(getData(currentFile))
                }
                else{
                    musicFile.add(currentFile)
                }
            }
        }
        return musicFile
    }
}
