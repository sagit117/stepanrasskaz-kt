package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.domain.role.RoleRepository
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.AccountPage

fun Route.accountRoute() {
    get("/account/{id}") {
        /** получаем данные пользователя */
        val connectUserData = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
        }

        // TODO: подумать как сделать более универсально
        if (call.parameters["id"].toString() != connectUserData?.id.toString() && connectUserData?.role != RoleRepository.ADMIN) {
            call.respond(HttpStatusCode.Unauthorized)

            return@get
        }

        /**
         * первым идет шаблон для отрисовки,
         * в параметрах обязательно переедаем шаблон страницы
         */
        call.respondHtmlTemplate(EmptyLayout(AccountPage())) {
            /**
             * передаем данные в шаблоны
             * некоторые компоненты потребляют эти данные
             */
            user = connectUserData
        }
    }
}