<template>
  <div> account {{user}}</div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { getUserRepositories } from "./hooks/hooks.users";

export default defineComponent({
  name: "Account",

  setup() {
    const params = document.location.pathname.split("/")
    const userId = ref<string>(params[params.length - 1])
    const { user, error, isLoading } = getUserRepositories(userId.value)

    return {
      user
    }
  }
})
</script>