package com.dreamsoftware.utils

import com.google.gson.Gson

interface JSONConvertible {
    fun toJSON(): String = Gson().toJson(this)
}