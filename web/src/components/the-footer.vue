<template>
  <a-layout-footer style="text-align: center">
    Song电子书<span v-if="user.id">你好:{{user.name}}</span>
  </a-layout-footer>
</template>

<script lang="ts">
import {computed, defineComponent} from 'vue';
  import store from "@/store";

  export default defineComponent({
    name: 'the-footer',
    setup(){
      const user = computed(() => store.state.user);

      return {
        user
      }
    }
  });
</script>