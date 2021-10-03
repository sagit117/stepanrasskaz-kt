package ru.axel.stepanrasskaz.domain.user.session

import kotlinx.coroutines.*
import ru.axel.stepanrasskaz.Config
import kotlin.collections.HashMap

/**
 * Храним данные о подключение пользователей
 */
object UserStack {
    private val hashMapUser: HashMap<String, UserData> = HashMap()

    fun addUser(id: String) {
        if (id !in hashMapUser) {
            hashMapUser[id] = UserData(System.currentTimeMillis(), System.currentTimeMillis())

            autoClear(id)
        } else {
            val data = hashMapUser[id]?.copy(dateTimeAtLastConnect = System.currentTimeMillis())
            hashMapUser[id] = data ?: UserData(System.currentTimeMillis(), System.currentTimeMillis())
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun autoClear(id: String) {
        GlobalScope.launch {

            delay(Config.lifeTimeUserID * 1000)

            if (id in hashMapUser) {
                val isLife = ((System.currentTimeMillis() - hashMapUser[id]?.dateTimeAtLastConnect!!) / 1000) < Config.lifeTimeUserID

                if (!isLife) {
                    hashMapUser.remove(id)
                } else {
                    autoClear(id)
                }
            }
        }
    }

    fun getUser(id: String): UserData? {
        return hashMapUser[id]
    }
}

data class UserData(val dateTimeAtFirstConnect: Long, val dateTimeAtLastConnect: Long)