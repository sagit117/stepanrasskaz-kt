package ru.axel.stepanrasskaz.domain.user.dto

/**
 * DTO для смены данных пользователя
 */
data class ChangeUserDataDTO(val id: String, val name: String, val zipCode: String, val address: String) {
    fun toObjectForSave(): Any {
        data class SaveObject(val name: String, val zipCode: String, val address: String)

        return SaveObject(name, zipCode, address)
    }
}
