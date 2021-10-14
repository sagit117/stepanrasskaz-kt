package ru.axel.stepanrasskaz.utils

import ru.axel.stepanrasskaz.domain.role.RoleRepository

/**
 * Как должна работать система доступов к реалму
 *
 * 1. Определяем маршрут - точнее его часть, ту с которой маршрут начанается, например /account
 * 2. Определяем роли, которым маршрут доступен или не доступен
 */

/**
 * Класс для хранения данных о черных и белых листах ролей, которым доступен маршрут
 */
data class AccessLists(val denied: List<RoleRepository>? = null, val accept: List<RoleRepository>? = null)

object ConfigSecureRoute {
    val realm = mapOf<String, AccessLists>(
        "/account" to AccessLists(accept = listOf(RoleRepository.USER, RoleRepository.ADMIN, RoleRepository.CLIENT))
    )
}