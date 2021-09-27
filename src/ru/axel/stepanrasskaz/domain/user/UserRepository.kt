package ru.axel.stepanrasskaz.domain.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import ru.axel.stepanrasskaz.domain.role.RoleRepository

data class UserRepository(
    @BsonId
    val id: Id<UserRepository> = newId(),
    val name: String = "",
    val email: String,
    val password: String,
    val role: RoleRepository = RoleRepository.TEMP
    )