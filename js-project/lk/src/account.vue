<template>
  <div class="account-wrapper">
    <div class="account-wrapper__title" id="email">
      <div style="margin-right: .3rem">Пользователь: </div>
      <!-- insert email from EmailData -->
    </div>
    <div class="account-wrapper__dateAtCreation">Дата регистрации: {{new Date(user?.dateTimeAtCreation).toLocaleDateString()}}</div>

    <AccountTopMenu :items="accountTopMenuItems" @selectedPage="setPage" />

    <div class="account__pages" v-if="isLoading === false && !error">
      <component :is="page" :user="user" />
    </div>

    <div class="spinner__wrapper" v-if="isLoading">
      <div class="spinner">...loading</div>
    </div>

    <div v-if="error">Код ошибки: {{error}}</div>

  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watchEffect } from 'vue'
import { getUserRepositories } from "./hooks/hooks.users";
// @ts-ignore
import Toast from "../../src/toasts"
// @ts-ignore
import { goRoute } from "../../src/utils"
// @ts-ignore
import AccountTopMenu from "./components/account-top-menu.vue"
import { accountTopMenuItems } from "./config/menu/account-top-menu";
// @ts-ignore
import EmailData from "../../src/emailData"

export default defineComponent({
  name: "Account",
  components: { AccountTopMenu },

  setup() {
    const params = document.location.pathname.split("/")
    const userId = ref<string>(params[params.length - 1])
    const { user, error, isLoading } = getUserRepositories(userId.value)
    const page = ref<any>(accountTopMenuItems[0].component || null)

    watchEffect(() => {
      if (error.value) {
        switch (error.value) {
          case 401:
            new Toast("Ошибка", "Нет доступа к данным", "ERROR", 3, () => goRoute("/")).render("toasts");
            break;

          default:
            new Toast("Ошибка", "Произошла внутренняя ошибка сервера", "ERROR", 3).render("toasts");
        }
      }

      if (user.value && !error.value) {
          new EmailData({
            id: user.value?.id || "",
            email: user.value?.email || "undefined",
            isConfirmEmail: user.value?.isConfirmEmail || false
          }, "email").render()
      }
    })

    function setPage(selectPage: any) {
      page.value = selectPage
    }

    return {
      user,
      isLoading,
      accountTopMenuItems,
      page,
      error,
      setPage
    }
  }
})
</script>

<style lang="scss" scoped>
.account-wrapper {
  width: 100%;
  height: 100%;
  padding: 2rem;

  &__title {
    display: flex;
    font-weight: 600;
  }

  &__dateAtCreation {
    font-size: .7rem;
    margin-bottom: 2rem;
  }
}

.account__pages {
  display: flex;
  box-shadow: 1px 1px 6px 0 var(--color-grey);
  width: 100%;
  height: 100%;
  border-bottom-right-radius: 1rem;
  border-bottom-left-radius: 1rem;
  padding: 1rem;
}

.spinner__wrapper {

}

@media (max-width: 1024px) {
  .account-wrapper {
    padding: 2rem .1rem .1rem .1rem;
  }
}
</style>