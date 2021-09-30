package ru.axel.stepanrasskaz

import io.ktor.util.*
import ru.axel.stepanrasskaz.domain.user.UserRepository

/**
 * Конфиг констант для приложения
 */
object Config {
    val appTitle: String = "StepanRasskaz.ru";  // TODO: вписать вывод из БД
    val appFavicon: String = "/static/favicon.png" // TODO: вписать вывод из БД
    val logoUrl: String = "/static/favicon.png" // TODO: вписать вывод из БД

    val userRepoAttributeKey = AttributeKey<UserRepository>("userRepo")

    val hashMapUserSize = 100 // TODO: вписать вывод из БД
}