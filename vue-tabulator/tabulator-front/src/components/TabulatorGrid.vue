<script setup>
import { ref, onMounted, toRaw, watch } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables"; //import Tabulator library

const props = defineProps({
  rows: {
    type: Array,
    required: true,
  },
  columns: {
    type: Array,
    required: true,
  },
  height: String,
});

const table = ref(null);
const tabulator = ref(null);

onMounted(() => {
  tabulator.value = new Tabulator(table.value, {
    layout: "fitColumns",
    reactiveData: true,
    data: toRaw(props.rows),
    columns: props.columns,
    height: props.height,
    pagination: true,
    paginationCounter: "rows",
  });
});

watch(
  () => props.rows,
  (newRows) => {
    tabulator.value.setData(toRaw(newRows));
  },
  { deep: true }
);
</script>

<template>
  <div ref="table"></div>
</template>

<style>
@import "~/tabulator-tables/dist/css/tabulator.min.css";
</style>
