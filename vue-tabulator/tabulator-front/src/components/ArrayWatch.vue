<script setup>
import { watch } from "vue";

const props = defineProps({
  arr: Array,
});

watch(
  () => props.arr,
  (newArr) => {
    console.log("new");
  },
  { deep: true }
);
</script>

<template>
  <h6>watch component</h6>
  <li v-for="e in arr" :key="e.id">{{ e.id }}: {{ e.name }}</li>
</template>
