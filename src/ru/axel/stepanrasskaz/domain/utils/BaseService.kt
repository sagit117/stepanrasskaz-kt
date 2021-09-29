package ru.axel.stepanrasskaz.domain.utils

import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.id.toId

/**
 * Базовый контроллер для наследования
 */
open class BaseService<T: Any>(val collection: CoroutineCollection<T>) {

    suspend fun insertOne(model: T) {
        collection.insertOne(model)
    }

    suspend fun findOneById(id: String): T? {
        return collection.findOneById(ObjectId(id))
    }
}