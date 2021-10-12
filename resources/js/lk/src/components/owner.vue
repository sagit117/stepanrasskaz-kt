<template>
  <div class="owner">
    <label for="name">ФИО:</label>
    <input type="text" id="name" class="mb2" v-model="name" placeholder="Введите ФИО полностью">

    <label for="zip">Индекс:</label>
    <input type="text" id="zip" class="mb2" v-model="zip" placeholder="Введите почтовый индекс">

    <label for="address">Адрес доставки:</label>
    <textarea id="address" class="mb2" v-model="address" placeholder="Введите адрес доставки"></textarea>

    <div class="owner__controls">
      <div class="btn btn-primary">Сохранить</div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue"

export default defineComponent({
  name: "Owner",
  props: {
    user: {
      type: Object,
      required: true
    }
  },

  setup(props) {
    const user = computed(() => props.user)
    const name = computed({
      get: () => user.value?.name || "",
      set: val => user.value.name = val
    })
    const zip = computed({
      get: () => user.value?.zipCode || "",
      set: val => user.value.zipCode = val
    })
    const address = computed({
      get: () => user.value?.address || "",
      set: val => user.value.address = val
    })

    return {
      user,
      name,
      zip,
      address
    }
  }
})
</script>


<style lang="scss" scoped>
.owner {
  width: 100%;

  &__controls {
    display: flex;
    justify-content: flex-end;
  }
}
</style>