<template>
  <div class="account-wrapper">
    <div class="account-wrapper__title">Пользователь: {{user?.email}}</div>
    <AccountTopMenu :items="accountTopMenuItems" />
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

export default defineComponent({
  name: "Account",
  components: { AccountTopMenu },

  setup() {
    const params = document.location.pathname.split("/")
    const userId = ref<string>(params[params.length - 1])
    const { user, error, isLoading } = getUserRepositories(userId.value)

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
    })

    return {
      user,
      isLoading,
      accountTopMenuItems
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
    font-weight: 600;
    margin-bottom: 2rem;
  }
}

@media (max-width: 1024px) {
  .account-wrapper {
    padding: 2rem .1rem .1rem .1rem;
  }
}
</style>