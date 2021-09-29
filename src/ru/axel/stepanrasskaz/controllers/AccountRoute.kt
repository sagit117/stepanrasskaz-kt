package ru.axel.stepanrasskaz.controllers

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import ru.axel.stepanrasskaz.Config
import ru.axel.stepanrasskaz.templates.layouts.EmptyLayout
import ru.axel.stepanrasskaz.templates.pages.AccountPage

fun Route.accountRoute() {
    get("/account") {
        /** получаем данные пользователя */
        val connectUserData = try {
            call.attributes[Config.userRepoAttributeKey]
        } catch (error: Exception) {
            null
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