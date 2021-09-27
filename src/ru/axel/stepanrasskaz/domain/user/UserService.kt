package ru.axel.stepanrasskaz.domain.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.utils.BaseService
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.sha256
import java.util.*

/**
 * Контроллер для всех операций с колекцией пользователей
 */
class UserService(collection: CoroutineCollection<UserRepository>): BaseService<UserRepository>(collection) {

    private suspend fun findOne(email: String): UserRepository? {
        return collection.findOne(UserRepository::email eq email)
    }

    fun checkAuth(userRepository: UserRepository, authDTO: AuthDTO): Boolean {
        println(userRepository)
        return  userRepository.password.trim() == authDTO.password.sha256()
    }

    suspend fun getUser(authDTO: AuthDTO): UserRepository? {
        return findOne(authDTO.getEmail())
    }

    fun createJWT(configJWT: ConfigJWT, userRepository: UserRepository): String? {
        return JWT.create()
            .withAudience(configJWT.audience)
            .withIssuer(configJWT.issuer)
            .withClaim("email", userRepository.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(configJWT.secret))
    }
}