package ru.axel.stepanrasskaz.domain.user

import org.litote.kmongo.coroutine.*

class UserController(private val database: CoroutineDatabase) {
    private val collection: CoroutineCollection<UserModel> = database.getCollection()

    fun getCollection(): CoroutineCollection<UserModel> {
        return collection
    }
}