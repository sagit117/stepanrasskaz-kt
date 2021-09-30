package ru.axel.stepanrasskaz.domain.user.helpers

import ru.axel.stepanrasskaz.Config.hashMapUserSize
import ru.axel.stepanrasskaz.domain.user.UserRepository

object HashMapUser {
    private val mapUser: HashMap<String, UserRepository> = HashMap()

    fun getUsers(id: String): UserRepository? {
        return mapUser[id]
    }

    fun addUsers(user: UserRepository) {
        // TODO: зможно стоит ограничить размер как-то по другому
        if (user.id.toString() !in mapUser) {
            if (mapUser.size == hashMapUserSize) mapUser.clear()

            mapUser[user.id.toString()] = user
        }
    }
}