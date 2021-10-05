### Добавление нового роутинга

Новый маршрут подключается через RootRouting

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

Вспомогательные классы для пользователя

> <b>HashMapUser</b> - <i>класс для хеширования запросов пользователей из БД</i>

> <b>UserSession</b> - <i>класс для хранения куки аутентификационного токена</i>

> <b>UserID</b> - <i>класс для хранения куки ID соединения</i>

> <b>UserStack</b> - <i>класс для хранения всех сессий пользователя</i>

```kotlin
data class UserDataMemory(
    val dateTimeAtFirstConnect: Long,
    val dateTimeAtLastConnect: Long,
    val dateTimeAtSendEmailChangeCode: Long? = null,
    val passwordChangeCode: String? = null,
    val userDbId: String? = null,
    val countRequestChangePass: Int = 0
)
```