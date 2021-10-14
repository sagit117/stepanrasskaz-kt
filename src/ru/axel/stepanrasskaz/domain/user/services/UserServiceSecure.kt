package ru.axel.stepanrasskaz.domain.user.services

import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.domain.role.RoleRepository
import ru.axel.stepanrasskaz.domain.user.UserRepository

/**
 * Безопасный класс для работы с сервисом пользователей
 * @param client - проверенные данные пользователя взятые из токена
 */
class UserServiceSecure(val client: UserRepository?) {
    /**
     * Проверяем доступ пользователя на загрузку данных пользователей с отличным ID
     */
    suspend fun findOneById(id: String): UserRepository? {
        return if (client != null &&
            (id == client.id.toString()
            || (client.role.indexOf(RoleRepository.ADMIN) > -1
                    && client.role.indexOf(RoleRepository.USER_DATA_READ) > -1))
        ) {
            UserService(DataBase.getCollection()).findOneById(id)
        } else {
            null
        }
    }
}