package ru.axel.stepanrasskaz.domain.utils

import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineCollection

/**
 * Базовый контроллер для наследования
 */
open class BaseService<T: Any>(val collection: CoroutineCollection<T>) {

    suspend fun insertOne(model: T) {
        collection.insertOne(model)
    }

    suspend fun findOneById(id: Id<T>): T? {
        return collection.findOneById(id)
    }
}