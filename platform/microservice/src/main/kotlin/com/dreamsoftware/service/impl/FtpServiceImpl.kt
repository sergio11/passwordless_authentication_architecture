package com.dreamsoftware.service.impl

import com.dreamsoftware.service.IFtpService
import org.apache.commons.io.FileUtils
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.nio.file.Files

class FtpServiceImpl(
    private val ftpClient: FTPClient
): IFtpService {

    private companion object {
        const val FTP_HOSTNAME = "ftp_repository"
        const val FTP_USER = "thunderotp"
        const val FTP_PASSWORD = "thunderotp00"
    }

    override fun getFile(fileName: String, fileExt: String): String {
        var downloadedFile: File? = null
        with(ftpClient) {
            try {
                println("try to connect to FTP Host: $FTP_HOSTNAME")
                connect(FTP_HOSTNAME)
                enterLocalPassiveMode()
                login(FTP_USER, FTP_PASSWORD)
                if(!FTPReply.isPositiveCompletion(replyCode)) {
                    disconnect()
                }
                println("try to download file ${fileName}.${fileExt}")
                val remoteFile = retrieveFileStream("$fileName.$fileExt")
                downloadedFile = File(Files.createTempDirectory(null).toFile(), "$fileName.$fileExt")
                println("copy to local file")
                FileUtils.copyInputStreamToFile(remoteFile, downloadedFile)
                logout()
            } catch (ex: Exception) {
                ex.printStackTrace()
                println("Ex occurred -> ${ex.message}")
            } finally {
                if(isConnected) {
                    disconnect()
                }
            }
            println("Downloaded file path -> ${downloadedFile?.absolutePath}")
            return downloadedFile?.absolutePath ?: throw IllegalStateException("File can not be downloaded")
        }
    }
}