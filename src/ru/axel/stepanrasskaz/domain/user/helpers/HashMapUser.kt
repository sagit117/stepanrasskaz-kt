package ru.axel.stepanrasskaz.domain.user.helpers

import ru.axel.stepanrasskaz.Config.hashMapUserSize
import ru.axel.stepanrasskaz.domain.user.UserRepository

/**
 * Объект для получения хеш данных пользователя не из базы
 */
object HashMapUser {
    private val mapUser: HashMap<String, UserRepository> = HashMap()

    fun getUsers(id: String): UserRepository? {
        return mapUser[id]
    }

    fun addUsers(user: UserRepository) {
        // TODO: возможно стоит ограничить размер как-то по другому
        if (user.id.toString() !in mapUser) {
            if (mapUser.size == hashMapUserSize) mapUser.clear()

            mapUser[user.id.toString()] = user
        }
    }

    fun removeUser(id: String) {
        if (id !in mapUser) {
            mapUser.remove(id)
        }
    }
}