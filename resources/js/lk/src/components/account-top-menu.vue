<template>
  <div class="accountTopMenu">
    <div
        v-for="item in items"
        :key="item.id"
        class="accountTopMenu__item"
        :class="[ item?.extClass ? item.extClass : '']"
        @click="$emit('selectedPage', item.component)"
    >
      {{item.title}}
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"

export default defineComponent({
  name: "AccountTopMenu",
  props: {
    items: {
      type: Array,
      required: true
    }
  },
  emits: ["selectedPage"],

  setup(props) {
    return {
      items: props.items
    }
  }
})
</script>

<style lang="scss" scoped>
.accountTopMenu {
  display: flex;
  box-shadow: 1px 1px 6px 0 var(--color-grey);
  width: 100%;
  height: 4rem;
  border-top-right-radius: 1rem;
  border-top-left-radius: 1rem;
  padding: 2px;

  &__item {
    display: flex;
    width: 100%;
    justify-content: center;
    align-items: center;
    text-transform: uppercase;
    font-size: .8rem;
    font-weight: 600;
    border-right: 1px solid var(--color-grey-light);
    cursor: pointer;
    user-select: none;

    &__red {
      color: var(--color-red);
    }
  }

  &__item:last-child {
    border-right: none;
  }
}
</style>