package com.dreamsoftware.service.impl

import com.dreamsoftware.repository.OTPRepository
import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPService

class OTPServiceImpl(
    private val OTPRepository: OTPRepository,
    private val OTPGenerator: OTPGenerator
): OTPService {


}