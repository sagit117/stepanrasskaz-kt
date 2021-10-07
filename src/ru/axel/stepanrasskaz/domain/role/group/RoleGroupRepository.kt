package ru.axel.stepanrasskaz.domain.role.group

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import ru.axel.stepanrasskaz.domain.role.RoleRepository

data class RoleGroupRepository(
    @BsonId
    val id: Id<RoleGroupRepository> = newId(),
    val name: String,
    val listRoles: List<RoleRepository>
)