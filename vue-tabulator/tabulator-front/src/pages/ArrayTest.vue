<script setup>
import { ref } from "vue";
import ArrayWatch from "components/ArrayWatch.vue";

const arr = ref([
  {
    id: "#Cust0",
    name: "홍길동",
    quantity: 50,
  },
  {
    id: "#Cust1",
    name: "Steve",
    quantity: 20,
  },
]);

function add() {
  arr.value.push({ id: "#Cust2", name: "test" });
}
</script>

<template>
  <q-btn @click="add">추가</q-btn>
  <array-watch :arr="arr" />
</template>
