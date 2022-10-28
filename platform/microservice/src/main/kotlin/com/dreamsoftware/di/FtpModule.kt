package com.dreamsoftware.di

import com.dreamsoftware.service.IFtpService
import com.dreamsoftware.service.impl.FtpServiceImpl
import org.apache.commons.net.ftp.FTPClient
import org.koin.dsl.module

val ftpModule = module {
    factory { FTPClient() }
    factory<IFtpService> { FtpServiceImpl(get()) }
}