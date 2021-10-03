"use strict";
import Api from "./api.js";
import Toast from "./toasts.js";
import Spinner from "./spinner.js";
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
const btnGoForgotPass = document.getElementById("go-forgot-password");
btnGoForgotPass?.addEventListener("click", () => {
    goRoute("/password/recovery");
});
const btnForgotPass = document.getElementById("forgot-password");
btnForgotPass?.addEventListener("click", () => preSendCheck(passwordSetCode));
const btnChangePassword = document.getElementById("change-password");
btnChangePassword?.addEventListener("click", () => preSendCheck(changePassword));
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
const inputPassCode = document.getElementById("code");
inputPassCode?.addEventListener("keypress", (event) => {
    if (event.key === "Enter" && btnChangePassword)
        preSendCheck(changePassword);
    inputPassCode?.classList.remove("input-error");
});
/**
 * Предварительная проверка введенных значений, перед отправкой на сервер
 * @param cb - функция выполниться если проверка прошла успешно
 */
function preSendCheck(cb) {
    /** login */
    if (inputEmail?.value === "") {
        inputEmail?.classList.add("input-error");
        new Toast("Предупреждение", "Email не должен быть пустым", "WARNING", 3)
            .render("toasts");
        return;
    }
    if (inputPassword?.value === "") {
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
    /** change pass */
    if (inputPassCode?.value === "") {
        inputPassCode?.classList.add("input-error");
        new Toast("Предупреждение", "Нужно ввести код из письма", "WARNING", 3)
            .render("toasts");
        return;
    }
    cb();
}
/**
 * обработчик кнопки войти
 */
function authClickHandler() {
    const spinner = new Spinner("auth-form");
    spinner.render("spinner-wrapper");
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
    })
        .finally(() => spinner.destroy());
}
/**
 * обработчик кнопки регистрация
 */
function registryClickHandler() {
    const spinner = new Spinner("auth-form");
    spinner.render("spinner-wrapper");
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
    })
        .finally(() => spinner.destroy());
}
/**
 * Обработчик кнопки выслать код
 */
function passwordSetCode() {
    const spinner = new Spinner("auth-form");
    spinner.render("spinner-wrapper");
    Api.passwordCodeSet({ login: inputEmail?.value })
        .then((res) => {
        if (!res.ok)
            throw new Error(String(res.status));
        new Toast("Учетные данные подтверждены", "Код выслан на указанные email", "SUCCESS", 3, () => {
            goRoute("/password/change");
        })
            .render("toasts");
    })
        .catch((error) => {
        switch (+error.message) {
            case 400:
            case 401:
                new Toast("Ошибка", "Не верный логин", "ERROR", 3).render("toasts");
                break;
            default:
                new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
        }
    })
        .finally(() => spinner.destroy());
}
/**
 * Обработчик кнопки сменить пароль
 */
function changePassword() {
    const spinner = new Spinner("auth-form");
    spinner.render("spinner-wrapper");
    Api.passwordChange({ code: inputPassCode?.value, password: inputPassword?.value })
        .then((res) => {
        if (!res.ok)
            throw new Error(String(res.status));
        new Toast("Учетные данные подтверждены", "Пароль успешно изменен", "SUCCESS", 3, () => {
            goRoute("/login");
        })
            .render("toasts");
    })
        .catch((error) => {
        switch (+error.message) {
            case 400:
            case 401:
                new Toast("Ошибка", "Не верный код для смены пароля", "ERROR", 3).render("toasts");
                break;
            default:
                new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
        }
    })
        .finally(() => spinner.destroy());
}
//# sourceMappingURL=auth-form.js.map