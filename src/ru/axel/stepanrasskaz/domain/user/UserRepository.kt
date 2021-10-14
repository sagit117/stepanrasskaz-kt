package ru.axel.stepanrasskaz.domain.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import ru.axel.stepanrasskaz.domain.role.RoleRepository

data class UserRepository(
    @BsonId
    val id: Id<UserRepository> = newId(),
    val name: String = "",
    val zipCode: String = "",
    val address: String = "",
    val email: String,
    val password: String,
    val role: MutableList<RoleRepository> = mutableListOf(RoleRepository.USER),
    val isBlocked: Boolean = false,         // перманентная блокировка
    val isNeedPassword: Boolean = false,    // блокировка до ввода пароля
    val dateTimeAtCreation: Long = System.currentTimeMillis(),
    val roleGroupsId: MutableList<String> = mutableListOf(""),
    val isConfirmEmail: Boolean = false
)