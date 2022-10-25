package com.dreamsoftware.service.impl

import com.dreamsoftware.service.ISftpService
import net.schmizz.sshj.SSHClient
import java.io.File
import java.nio.file.Files

class SftpServiceImpl(
    private val sshClient: SSHClient
): ISftpService {

    override fun getFile(folderName: String, fileName: String, fileExt: String): String {
        sshClient.newSFTPClient().use { client ->
            with(client) {
                var downloadedFile: File? = null
                for (remoteResource in ls(folderName)) {
                    if(remoteResource.name == "$fileName.$fileExt") {
                        println("$fileName.$fileExt Found!, downloading it ....")
                        downloadedFile = File(Files.createTempDirectory(null).toFile(), "$fileName.$fileExt")
                        get(remoteResource.path, downloadedFile.absolutePath)
                        break
                    }
                }
                return downloadedFile?.absolutePath ?: throw IllegalStateException("File: $fileName.$fileExt can not be opened")
            }
        }
    }
}