<template>
  <div> account {{user}}</div>
</template>

<script lang="ts">
import {defineComponent, onMounted, Ref, ref} from 'vue'
import { getUserRepositories } from "./hooks/hooks.users";
import {IUser} from "./interfaces";

export default defineComponent({
  name: "Account",

  setup() {
    const params = document.location.pathname.split("/")
    const userId: Ref<string> = ref(params[params.length - 1])
    const user: Ref<IUser | null> = ref(null)

    onMounted(() => {
      user.value = getUserRepositories(userId.value)
    })

    return {
      user
    }
  }
})
</script>