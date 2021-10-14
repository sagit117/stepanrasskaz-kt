<template>
  <div class="owner">
    <label for="name" :class="[!name ? 'label__warning_icon' : '']">ФИО:</label>
    <input type="text" id="name" class="mb2" v-model="name" placeholder="Введите ФИО полностью">

    <label for="zip" :class="[!zipCode ? 'label__warning_icon' : '']">Индекс:</label>
    <input type="text" id="zip" class="mb2" v-model="zipCode" placeholder="Введите почтовый индекс">

    <label for="address" :class="[!address ? 'label__warning_icon' : '']">Адрес доставки:</label>
    <textarea id="address" class="mb2" v-model="address" placeholder="Введите адрес доставки"></textarea>

    <div class="owner__controls">
      <div class="btn btn-primary" @click="saveUser">Сохранить</div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue"
import { saveUserRepositories } from "../hooks/hooks.users";

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
    const zipCode = computed({
      get: () => user.value?.zipCode || "",
      set: val => user.value.zipCode = val
    })
    const address = computed({
      get: () => user.value?.address || "",
      set: val => user.value.address = val
    })

    function saveUser() {
      saveUserRepositories({
        id: user.value.id,
        name: name.value,
        zipCode: zipCode.value,
        address: address.value
      })
    }

    return {
      user,
      name,
      zipCode,
      address,
      saveUser
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
    height: 3rem;
  }
}
</style>