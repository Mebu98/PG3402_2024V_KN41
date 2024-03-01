<script setup>

import {getChallengesCount} from "@/functions/analyticsFunctions.js";
import {computed, reactive, ref} from "vue";

let count = reactive({
  value: 0
});

getChallengesCount(count);

</script>

<template>
  <v-app>
    <v-main
        style="display: flex; place-items: center; place-content: center; flex-direction: column">
      <header>
        <h1>Math Challenges</h1>
      </header>
      <RouterView/>
      <v-card>
        <h1>analytics</h1>
        <div>Number of challenges generated: {{count.value}}</div>
        <v-btn @click="getChallengesCount(count)">Refresh</v-btn>
      </v-card>
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
