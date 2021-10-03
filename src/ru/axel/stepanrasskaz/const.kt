package ru.axel.stepanrasskaz

import io.ktor.util.*
import ru.axel.stepanrasskaz.domain.user.UserRepository

/**
 * Конфиг констант для приложения
 */
object Config {
    const val appTitle: String = "StepanRasskaz.ru";  // TODO: вписать вывод из БД
    const val appFavicon: String = "/static/favicon.png" // TODO: вписать вывод из БД
    const val logoUrl: String = "/static/favicon.png" // TODO: вписать вывод из БД

    val userRepoAttributeKey = AttributeKey<UserRepository>("userRepo")

    const val hashMapUserSize: Int = 100 // TODO: вписать вывод из БД
    const val lifeTimeUserID: Long = 60 // время жизни куки userID
}