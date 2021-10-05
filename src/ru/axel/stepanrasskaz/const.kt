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

    const val hashMapUserSize: Int = 500 // размер хеша пользователей
    const val lifeTimeUserID: Long = 900 // время жизни куки userID
    const val maxCountRequestChangePass: Int = 5 // максимальное число попыток ввода кода для смены пароля
    const val periodSendEmailChangePass: Long = 60 // период, который должен пройти между отправками писем смены кода
}