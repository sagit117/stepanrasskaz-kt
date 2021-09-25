package ru.axel.stepanrasskaz.utils

import java.security.MessageDigest

/**
 * Функции расширения
 */
fun hash(input: String): String {
    val bytes = input.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}

fun String.sha256(): String {
    return hash(this)
}