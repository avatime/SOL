package com.finance.android.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern

@SuppressLint("SimpleDateFormat")
fun validateBirthday(birthday: String): Boolean {
    if (birthday.length < 6) {
        return false
    }

    return try {
        val dateFormat = SimpleDateFormat("yyMMdd")
        dateFormat.isLenient = false
        dateFormat.parse(birthday)
        true
    } catch (e: ParseException) {
        false
    }
}

fun validatePhoneNum(phoneNum: String): Boolean {
    return Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phoneNum)
}