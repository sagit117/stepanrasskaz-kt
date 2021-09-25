package ru.axel.stepanrasskaz.domain.user

import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq

/**
 * Контроллер для всех операций с колекцией пользователей
 */
class UserController(database: CoroutineDatabase) {
    private val collection: CoroutineCollection<UserModel> = database.getCollection()

    suspend fun insertOne(userModel: UserModel) {
        collection.insertOne(userModel)
    }

    suspend fun findOne(email: String): UserModel? {
        return collection.findOne(UserModel::email eq email)
    }
}