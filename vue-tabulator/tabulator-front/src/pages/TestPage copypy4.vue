<script setup>
import { ref, reactive, onMounted, toRaw, watch } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables";
import axios from "axios";

const columns = [
  { title: "No.", field: "rownum", formatter: "rownum", width: 50 },
  {
    formatter: "rowSelection",
    titleFormatter: "rowSelection",
    headerSort: false,
    width: 20,
  },
  { title: "ID", field: "id", width: 150 },
  { title: "Name", field: "name", width: 150 },
  { title: "Age", field: "age", hozAlign: "left" },
];
const rows = ref([
  { id: "id1", name: "Oli Bob", age: "12" },
  {
    id: "id2",
    name: "Mary May",
    age: "1",
  },
  {
    id: "id3",
    name: "Christine Lobowski",
    age: "42",
  },
  {
    id: "id4",
    name: "Brendon Philips",
    age: "125",
  },
  {
    id: "id5",
    name: "Margret Marmajuke",
    age: "16",
  },
]);

const table = ref(null);
const tabulator = ref(null);
onMounted(() => {
  tabulator.value = new Tabulator(table.value, {
    height: 400,
    layout: "fitColumns",
    reactiveData: true,
    data: toRaw(rows.value),
    columns: columns,
    //
    // footerElement: `<div></div><div class='grid-footer'>1-${rows.value.length} of ${rows.value.length}</div>`,
    pagination: true,
    paginationCounter: "rows",
  });
});

function addEmptyRow() {
  rows.value.push({});
  // tabulator.value.addRow({});
  tabulator.value.redraw(true);
}
function deleteRows() {
  const selectedRows = tabulator.value.getSelectedRows();
  selectedRows.forEach((row) => row.delete());
}
</script>

<template>
  <span>test</span>
  <div ref="table"></div>
  <q-btn @click="addEmptyRow">추가</q-btn>
  <q-btn @click="deleteRows">삭제</q-btn>
</template>

<style>
@import "~/tabulator-tables/dist/css/tabulator.min.css";
</style>
