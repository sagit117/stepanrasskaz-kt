"use strict";
import Api from "./api.js";
import Toast from "./toasts.js";
import { goRoute } from "./utils.js";
/** buttons */
const btnAuth = document.getElementById("auth");
btnAuth?.addEventListener("click", () => preSendCheck(authClickHandler));
const btnGoRegistry = document.getElementById("go-registry");
btnGoRegistry?.addEventListener("click", () => {
    goRoute("/registry");
});
const btnGoAuth = document.getElementById("go-auth");
btnGoAuth?.addEventListener("click", () => {
    goRoute("/login");
});
const btnRegistry = document.getElementById("registry");
btnRegistry?.addEventListener("click", () => preSendCheck(registryClickHandler));
const btnForgotPass = document.getElementById("go-forgot-password");
btnForgotPass?.addEventListener("click", () => {
    goRoute("/recovery/password");
});
/** inputs */
const inputEmail = document.getElementById("email");
inputEmail?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnAuth)
        preSendCheck(authClickHandler);
    if (event.key === "Enter" && btnRegistry)
        preSendCheck(registryClickHandler);
    inputEmail?.classList.remove("input-error");
});
const inputPassword = document.getElementById("password");
inputPassword?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnAuth)
        preSendCheck(authClickHandler);
    if (event.key === "Enter" && btnRegistry)
        preSendCheck(registryClickHandler);
    inputPassword?.classList.remove("input-error");
});
const inputPasswordConfirm = document.getElementById("password-confirm");
inputPasswordConfirm?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnRegistry)
        preSendCheck(registryClickHandler);
    inputPasswordConfirm?.classList.remove("input-error");
});
/**
 * Предварительная проверка введенных значений, перед отправкой на сервер
 * @param cb - функция выполниться если проверка прошла успешно
 */
function preSendCheck(cb) {
    if (!inputEmail?.value) {
        inputEmail?.classList.add("input-error");
        new Toast("Предупреждение", "Email не должен быть пустым", "WARNING", 3)
            .render("toasts");
        return;
    }
    if (!inputPassword?.value) {
        inputPassword?.classList.add("input-error");
        new Toast("Предупреждение", "Пароль не должен быть пустым", "WARNING", 3)
            .render("toasts");
        return;
    }
    /** registry */
    if (inputPasswordConfirm && (inputPassword?.value !== inputPasswordConfirm?.value)) {
        inputPasswordConfirm?.classList.add("input-error");
        new Toast("Предупреждение", "Пароль должен совпадать с подтверждением пароля", "WARNING", 3)
            .render("toasts");
        return;
    }
    cb();
}
/**
 * обработчик кнопки войти
 */
function authClickHandler() {
    Api.auth(inputEmail?.value, inputPassword?.value)
        .then((res) => {
        if (res.ok)
            return res.json();
        throw new Error(String(res.status));
    })
        .then((res) => {
        localStorage.setItem("token", res.token);
        new Toast("Учетные данные подтверждены", "Вы вошли в систему", "SUCCESS", 3, () => {
            goRoute("/");
        })
            .render("toasts");
    })
        .catch((error) => {
        switch (+error.message) {
            case 400:
            case 401:
                new Toast("Ошибка", "Не верный логин или пароль", "ERROR", 3).render("toasts");
                break;
            default:
                new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
        }
    });
}
/**
 * обработчик кнопки регистрация
 */
function registryClickHandler() {
    Api.registry({ login: inputEmail?.value, password: inputPassword?.value })
        .then((res) => {
        if (!res.ok)
            throw new Error(String(res.status));
        new Toast("Учетные данные подтверждены", "Вы зарегистрированы в системе", "SUCCESS", 3, () => {
            goRoute("/login");
        })
            .render("toasts");
    })
        .catch((error) => {
        switch (+error.message) {
            case 400:
            case 401:
                new Toast("Ошибка", "Проверьте email, возможно он уже зарегистрирован", "ERROR", 3).render("toasts");
                break;
            default:
                new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
        }
    });
}
//# sourceMappingURL=auth-form.js.map