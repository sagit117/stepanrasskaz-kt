package ru.axel.stepanrasskaz.domain.user

import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import ru.axel.stepanrasskaz.domain.utils.BaseController

/**
 * Контроллер для всех операций с колекцией пользователей
 */
class UserController(collection: CoroutineCollection<UserModel>): BaseController<UserModel>(collection) {

    suspend fun findOne(email: String): UserModel? {
        return collection.findOne(UserModel::email eq email)
    }
}