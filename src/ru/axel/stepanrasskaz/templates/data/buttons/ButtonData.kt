package ru.axel.stepanrasskaz.templates.data.buttons

/**
 * Класс данных для стандартной кнопки
 */
data class ButtonData(val id: String, val title: String, val classes: Set<String>, val iconUrl: String? = null)