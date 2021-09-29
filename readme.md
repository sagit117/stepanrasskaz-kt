### Добавление нового роутинга

```kotlin
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
```