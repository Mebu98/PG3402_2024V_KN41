<script setup>
</script>

<template>
  <v-app>
    <v-main
        style="display: flex; place-items: center; place-content: center; flex-direction: column"
    >
      <RouterView/>
    </v-main>
  </v-app>
</template>

<style>
header {
  line-height: 1.5;
}


v-main {
  display: flex;
  place-items: center;
}
</style>
