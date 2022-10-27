package com.dreamsoftware.di

import com.dreamsoftware.service.ISftpService
import com.dreamsoftware.service.impl.SftpServiceImpl
import com.dreamsoftware.utils.connect
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import org.koin.dsl.module

const val SFTP_HOSTNAME = "ftp_repository"
const val SFTP_PORT = 21
const val SFTP_USER = "thunderotp"
const val SFTP_PASSWORD = "thunderotp00"

const val CONNECTION_RETRIES = 10

val sftpModule = module {
    factory {
        SSHClient().apply {
            addHostKeyVerifier(PromiscuousVerifier())
        }.also {
            it.connect(SFTP_HOSTNAME, SFTP_PORT, CONNECTION_RETRIES)
            it.authPassword(SFTP_USER, SFTP_PASSWORD)
        }
    }
    factory<ISftpService> { SftpServiceImpl(get()) }
}