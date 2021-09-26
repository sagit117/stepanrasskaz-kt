package ru.axel.stepanrasskaz.domain.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import ru.axel.stepanrasskaz.domain.user.auth.dto.AuthDTO
import ru.axel.stepanrasskaz.domain.utils.BaseController
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.sha256
import java.util.*

/**
 * Контроллер для всех операций с колекцией пользователей
 */
class UserService(collection: CoroutineCollection<UserModel>): BaseController<UserModel>(collection) {

    private suspend fun findOne(email: String): UserModel? {
        return collection.findOne(UserModel::email eq email)
    }

    fun checkAuth(userModel: UserModel, authDTO: AuthDTO): Boolean {
        return  userModel.password == authDTO.password.sha256()
    }

    suspend fun getUser(authDTO: AuthDTO): UserModel? {
        return findOne(authDTO.getEmail())
    }

    fun createJWT(configJWT: ConfigJWT, userModel: UserModel): String? {
        return JWT.create()
            .withAudience(configJWT.audience)
            .withIssuer(configJWT.issuer)
            .withClaim("email", userModel.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(configJWT.secret))
    }
}