package ru.axel.stepanrasskaz.domain.user.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.RegistryDTO
import ru.axel.stepanrasskaz.domain.user.helpers.HashMapUser
import ru.axel.stepanrasskaz.domain.utils.BaseService
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.sha256
import java.util.*

// TODO: удалять запись из hashMapUser если есть изменения по пользователю

/**
 * Контроллер для всех операций с колекцией пользователей
 */
class UserService(collection: CoroutineCollection<UserRepository>): BaseService<UserRepository>(collection) {

    private suspend fun findOne(email: String): UserRepository? {
        return collection.findOne(UserRepository::email eq email)
    }

    fun checkAuth(userRepository: UserRepository, authDTO: AuthDTO): Boolean {
        return  userRepository.password.trim() == authDTO.password.sha256()
    }

    suspend fun getUser(dto: Any): UserRepository? {
        return when(dto) {
            is AuthDTO -> findOne(dto.getEmail())
            is RegistryDTO -> findOne(dto.getEmail())
            is String -> findOne(dto)
            else -> null
        }
    }

    fun createJWT(configJWT: ConfigJWT, userRepository: UserRepository): String {
        return JWT.create()
            .withAudience(configJWT.audience)
            .withIssuer(configJWT.issuer)
            .withClaim("id", userRepository.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + 2592000000)) // 30 days
            .sign(Algorithm.HMAC256(configJWT.secret))
    }

    suspend fun insertOne(registryDTO: RegistryDTO): InsertOneResult {
        return collection.insertOne(UserRepository(email = registryDTO.getEmail(), password = registryDTO.password.sha256()))
    }
}