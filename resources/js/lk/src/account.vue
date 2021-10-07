<template>
  <div> account {{user}}</div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'

export default defineComponent({
  name: "Account",

  setup() {
    const user = ref<String>("test@test")

    return {
      user
    }
  }
})
</script>