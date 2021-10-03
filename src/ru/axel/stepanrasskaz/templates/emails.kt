package ru.axel.stepanrasskaz.templates

fun registryMail(): String {
    return "<b>Вы успешно зарегистрировались на ресурсе!</b>"
}

fun entryMail(): String {
    return "<b>Вы вошли в систему, если это были не Вы, восстановите пароль!</b>"
}

fun codeForChangePassword(code: String): String {
    return "<b>Ваш код для востановления пароля $code, введите его в соответствующее поле</b>"
}