package ru.axel.stepanrasskaz.utils

data class ConfigJWT(val secret: String, val issuer: String, val audience: String, val clientRealm: String)