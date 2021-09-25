package ru.axel.stepanrasskaz.domain.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class UserModel(
    @BsonId
    val id: Id<UserModel> = newId(),
    val name: String,
    val email: String,
    val password: String
    )