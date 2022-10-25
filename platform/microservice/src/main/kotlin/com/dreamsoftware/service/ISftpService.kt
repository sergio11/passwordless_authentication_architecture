package com.dreamsoftware.service

interface ISftpService {

    fun getFile(folderName: String, fileName: String, fileExt: String): String
}