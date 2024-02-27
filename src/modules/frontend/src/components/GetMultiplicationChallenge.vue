<script setup>
import {ref} from "vue";

let challenge = ref();
let userName = ref('');
let difficulty = ref(1);

async function generateMultiplicationChallenge() {
  await fetch(`http://localhost:8080/api/v1/multiplication/generate/username/${userName.value}/difficulty/${difficulty.value}`,
      {
        method: 'GET'
      }
  ).then(response => response.json().then(data => {
    console.log(data);
    challenge.value = data;
  }))
}

</script>

<template>
      <v-text-field v-model="userName" label="Username"></v-text-field>
      <v-text-field v-model="difficulty" label="Difficulty" type="number"></v-text-field>

    <button @click="generateMultiplicationChallenge">Generate Multiplication Challenge</button>
    <div>
      <div v-if="challenge">{{ challenge }}</div>
      <div v-else>No data</div>
    </div>
</template>

<style scoped>

</style>