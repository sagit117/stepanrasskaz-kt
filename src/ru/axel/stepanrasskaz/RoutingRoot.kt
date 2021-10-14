package ru.axel.stepanrasskaz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import ru.axel.stepanrasskaz.Config.userRepoAttributeKey
import ru.axel.stepanrasskaz.connectors.DataBase
import ru.axel.stepanrasskaz.controllers.accountRoute
import ru.axel.stepanrasskaz.controllers.apiRoute
import ru.axel.stepanrasskaz.controllers.homeRouting
import ru.axel.stepanrasskaz.controllers.authRoute
import ru.axel.stepanrasskaz.domain.user.UserRepository
import ru.axel.stepanrasskaz.domain.user.services.UserService
import ru.axel.stepanrasskaz.domain.user.session.UserSession
import ru.axel.stepanrasskaz.domain.user.helpers.HashMapUser
import ru.axel.stepanrasskaz.domain.user.session.UserID
import ru.axel.stepanrasskaz.domain.user.session.UserStack
import ru.axel.stepanrasskaz.utils.ConfigJWT
import ru.axel.stepanrasskaz.utils.ConfigMailer
import ru.axel.stepanrasskaz.utils.ConfigSecureRoute
import ru.axel.stepanrasskaz.utils.randomCode

/**
 * Объединяем все модули с маршрутами
 */
@Suppress("unused") // Referenced in application.conf
fun Application.moduleRoutingRoot(testing: Boolean = false) {
    /** настройки jwt */
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val clientRealm = environment.config.property("jwt.realm").getString()

    val configJWT = ConfigJWT(secret, issuer, audience, clientRealm)

    val jwtVerifier = JWT
        .require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    /** настройки mailer */
    val hostName = environment.config.property("mailer.hostName").getString()
    val smtpPort = environment.config.property("mailer.smtpPort").getString().toInt()
    val user = environment.config.property("mailer.user").getString()
    val password = environment.config.property("mailer.password").getString()
    val isSSLOnConnect = environment.config.property("mailer.isSSLOnConnect").getString().toBoolean()
    val from = environment.config.property("mailer.from").getString()
    val charSet = environment.config.property("mailer.charSet").getString()

    val configMailer = ConfigMailer(hostName, smtpPort, user, password, from, isSSLOnConnect, charSet)

    routing {
        if (!testing) {
            /**
             * перехват куки token и получение данных пользователя
             * определение доступности маршрута на основание данных в ConfigSecureRoute
             */
            intercept(ApplicationCallPipeline.Features) {
                if (!call.request.uri.startsWith("/static/")) {
                    val token = call.sessions.get<UserSession>()?.token

                    val verifierToken = try {
                        jwtVerifier.verify(token)
                    } catch (error: Exception) {
                        null
                    }

                    val id = verifierToken?.getClaim("id")?.asString()
                    val userService = UserService(DataBase.getCollection())

                    val userRepository: UserRepository? = id?.let {
                        /** хеширование запросов к бд */
                        val hashUser = HashMapUser.getUsers(id)

                        if (hashUser != null) {
                            return@let hashUser
                        } else {
                            val dbUser = userService.findOneById(it)

                            if (dbUser != null) {
                                HashMapUser.addUsers(dbUser)

                                return@let dbUser
                            } else {
                                return@let null
                            }
                        }
                    }

                    /**
                     * Кого мы пропускаем
                     * Если ограничения по маршруту не заполнены, то всех
                     * Если ограничения заполнены, то только в пределах ограничений
                     * Если у пользователя роль попадает в заблокированные, то разрешенные уже не проверяем
                     * Если у пользователя нет разрешенной роли - блокируем, иначе пропускаем
                     */
                    val key = ConfigSecureRoute.realm.keys.find { call.request.uri.startsWith(it) }

                    /** если пользователь определен */
                    if (userRepository != null) {
                        if (key == null) {
                            /**
                             * Если нет записей о правах доступа
                             * передаем в дальнейшие вызывы проверенные данные пользователя
                             */
                            call.attributes.put(userRepoAttributeKey, userRepository)
                            proceed()
                        } else {
                            val deniedList = ConfigSecureRoute.realm[key]?.denied
                            val acceptList = ConfigSecureRoute.realm[key]?.accept

                            val isDenied: Boolean = if (!deniedList.isNullOrEmpty()) {
                                userRepository.role.intersect(deniedList).isNotEmpty()
                            } else {
                                false
                            }

                            /** если роль пользователя не попала в запрещенные роли */
                            if (!isDenied) {
                                if (!acceptList.isNullOrEmpty()) {
                                    if (userRepository.role.intersect(acceptList).isNotEmpty()) {
                                        /**
                                         * Если найдена роль пользователя в разрешенных ролях
                                         * передаем в дальнейшие вызывы проверенные данные пользователя
                                         */
                                        call.attributes.put(userRepoAttributeKey, userRepository)
                                        proceed()
                                    } else {
                                        /** доступ заблокирован */
                                        call.respond(HttpStatusCode.Unauthorized)
                                        finish()
                                    }
                                } else {
                                    /**
                                     * Если запрета нет и нет записей о разрешенных ролях
                                     * передаем в дальнейшие вызывы проверенные данные пользователя
                                     */
                                    call.attributes.put(userRepoAttributeKey, userRepository)
                                    proceed()
                                }
                            } else {
                                /** доступ заблокирован */
                                call.respond(HttpStatusCode.Unauthorized)
                                finish()
                            }
                        }
                    } else {
                        /** есть запись с правами доступа, но пользователь не авторизован */
                        if (key !== null) {
                            /** доступ заблокирован */
                            call.respond(HttpStatusCode.Unauthorized)
                            finish()
                        } else {
                            proceed()
                        }
                    }
                }
            }

            /** перехват куки userID и получение данных пользователя */
            intercept(ApplicationCallPipeline.Features) {
                if (!call.request.uri.startsWith("/static/")) {
                    val userID = call.sessions.get<UserID>()?.id

                    if (userID == null) {
                        val code = randomCode(15)

                        call.sessions.set(UserID(id = code))

                        UserStack.addUser(code)
                    } else {
                        UserStack.addUser(userID)
                    }
                }

                proceed()
            }
        }

        apiRoute(configJWT, configMailer)
        authRoute()
        homeRouting()
        accountRoute()
    }
}