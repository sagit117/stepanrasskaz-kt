package ru.axel.stepanrasskaz.domain.user.session

import kotlinx.coroutines.*
import ru.axel.stepanrasskaz.Config
import kotlin.collections.HashMap

/**
 * Храним данные о подключение пользователей
 */
object UserStack {
    private val hashMapUser: HashMap<String, UserDataMemory> = HashMap()

    fun addUser(id: String) {
        if (id !in hashMapUser) {
            hashMapUser[id] = UserDataMemory(System.currentTimeMillis(), System.currentTimeMillis())

            autoClear(id)
        } else {
            val data = hashMapUser[id]?.copy(dateTimeAtLastConnect = System.currentTimeMillis())
            hashMapUser[id] = data ?: UserDataMemory(System.currentTimeMillis(), System.currentTimeMillis())
        }
    }

    /**
     * Автоочищение стэка
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun autoClear(id: String) {
        GlobalScope.launch {

            delay(Config.lifeTimeUserID * 1000)

            if (id in hashMapUser) {
                val isLife =
                    ((System.currentTimeMillis() - hashMapUser[id]?.dateTimeAtLastConnect!!) / 1000) < Config.lifeTimeUserID

                if (!isLife) {
                    hashMapUser.remove(id)
                } else {
                    autoClear(id)
                }
            }
        }
    }

    fun getUser(id: String): UserDataMemory? {
        return hashMapUser[id]
    }

    /**
     * Установить код для смены пароля
     */
    fun setPassCode(id: String, code: String?) {
        val data = hashMapUser[id]?.copy(passwordChangeCode = code, dateTimeAtSendEmailChangeCode = System.currentTimeMillis())

        if (data != null) {
            hashMapUser[id] = data
        }
    }

    /**
     * Установить id пользователя в БД
     */
    fun setUserDbId(id: String, dbId: String) {
        val data = hashMapUser[id]?.copy(userDbId = dbId)

        if (data != null) {
            hashMapUser[id] = data
        }
    }

    /**
     * Установить количество попыток ввода кода для смены пароля
     */
    fun setCountRequestChangePass(id: String, count: Int? = null) {
        val data = if (count == null) {
            hashMapUser[id]?.copy(countRequestChangePass = hashMapUser[id]?.countRequestChangePass?.plus(1) ?: 0)
        } else {
            hashMapUser[id]?.copy(countRequestChangePass = count)
        }

        if (data != null) {
            hashMapUser[id] = data
        }
    }
}

data class UserDataMemory(
    val dateTimeAtFirstConnect: Long,
    val dateTimeAtLastConnect: Long,
    val dateTimeAtSendEmailChangeCode: Long? = null,
    val passwordChangeCode: String? = null,
    val userDbId: String? = null,
    val countRequestChangePass: Int = 0
)