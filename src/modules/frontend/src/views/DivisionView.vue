<script setup>
import { ref } from "vue";
import {generateDivisionChallenge, submitDivisionChallenge} from "@/functions/divisionFunctions.js";

let challenge = ref({});
let userName = ref("");
let difficulty = ref(1);

let userAnswer = ref("");
let submitResponse = ref({});

let rules = {
  required: value => !!value || "Field is required.",
  maxInt: value => value <= 10 || "Value must be less than or equal to 10",
  minInt: value => value >= 1 || "Value must be more than or equal to 1",
}


</script>

<template>
  <div style="width: 30vw; display: flex; align-content: center; flex-direction: column">
    <v-sheet id="generate-challenge">
      <v-form @submit.prevent="(userName !== '' && difficulty >= 1 && difficulty <= 10) ?
            generateDivisionChallenge(userName, difficulty, challenge) : null">
        <v-text-field v-model="userName" :rules="[rules.required]" label="Username"></v-text-field>
        <v-text-field v-model="difficulty" :rules="[rules.maxInt, rules.minInt]" label="Difficulty" type="number"></v-text-field>
        <v-btn type="submit"> Generate Division Challenge </v-btn>
      </v-form>

    </v-sheet>

    <v-sheet id="display-challenge">
      <h1>Challenge:</h1>
      <div v-if="challenge.value != null">
        <p>game ID: {{challenge.value.id}}</p>
        <h1>{{challenge.value.num1}} / {{challenge.value.num2}}</h1>
      </div>
      <div v-else>
        <p>No challenge generated yet</p>
      </div>
      <v-form @submit.prevent="submitDivisionChallenge(challenge.value, userAnswer, submitResponse)">
        <v-text-field v-model="userAnswer" label="Your Answer"></v-text-field>
        <v-btn type="submit">Submit Answer</v-btn>
      </v-form>
      <div v-if="submitResponse.value && challenge.value">
        <p>response: {{challenge.value.num1}} / {{challenge.value.num2}} = {{userAnswer}}</p>
        <p>{{submitResponse.value}}</p>
      </div>
    </v-sheet>
  </div>
</template>

<style scoped>
</style>