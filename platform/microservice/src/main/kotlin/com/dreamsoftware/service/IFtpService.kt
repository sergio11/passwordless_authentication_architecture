package com.dreamsoftware.service

interface IFtpService {

    fun getFile(fileName: String, fileExt: String): String
}