package ru.axel.stepanrasskaz.domain.role

enum class RoleRepository {
    USER /** пользователь, который только зарегистрировался */, ADMIN, CLIENT, USER_DATA_READ, USER_DATA_WRITE, USER_DATA_CREATE
}