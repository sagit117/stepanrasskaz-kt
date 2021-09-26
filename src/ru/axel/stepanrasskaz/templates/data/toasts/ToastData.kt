package ru.axel.stepanrasskaz.templates.data.toasts

data class ToastData(val title: String, val message: String, val timer: Int, val type: TypeToast)

enum class TypeToast {
    ERROR, SUCCESS, WARNING
}