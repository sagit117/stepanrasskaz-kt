package ru.axel.stepanrasskaz.domain.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.result.InsertOneResult
import org.bson.BsonValue
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.user.auth.dto.RegistryDTO
import ru.axel.stepanrasskaz.domain.utils.BaseService
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.sha256
import java.util.*
import kotlin.reflect.KClass

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
            else -> null
        }
    }

    fun createJWT(configJWT: ConfigJWT, userRepository: UserRepository): String? {
        return JWT.create()
            .withAudience(configJWT.audience)
            .withIssuer(configJWT.issuer)
            .withClaim("email", userRepository.email)
            .withClaim("role", userRepository.role.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(configJWT.secret))
    }

    suspend fun insertOne(registryDTO: RegistryDTO): BsonValue? {
        return collection.insertOne(UserRepository(email = registryDTO.getEmail(), password = registryDTO.password.sha256())).insertedId
    }
}