<script setup>
import { ref, reactive, onMounted, toRaw } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables"; //import Tabulator library
import axios from "axios";

const table = ref(null); //reference to your table element
const tabulator = ref(null); //variable to hold your table

const tableData = reactive([]); //data for table to display
const rows = ref([]);

onMounted(() => {
  tabulator.value = new Tabulator(table.value, {
    height: 500,
    layout: "fitColumns",
    reactiveData: true,
    data: toRaw(tableData),
    columns: [
      { title: "No.", field: "rownum", formatter: "rownum", width: 50 },
      { title: "ID", field: "id", width: 150 },
      { title: "Name", field: "name", width: 150 },
      { title: "Age", field: "age", hozAlign: "left" },
    ],
    //
    // footerElement: `<div></div><div class='grid-footer'>1-${rows.value.length} of ${rows.value.length}</div>`,
    pagination: true,
    paginationCounter: "rows",
  });
});

tableData.push({ id: "id1", name: "Oli Bob", age: "12" });
tableData.push({
  id: "id2",
  name: "Mary May",
  age: "1",
});
tableData.push({
  id: "id3",
  name: "Christine Lobowski",
  age: "42",
});
tableData.push({
  id: "id4",
  name: "Brendon Philips",
  age: "125",
});
tableData.push({
  id: "id5",
  name: "Margret Marmajuke",
  age: "16",
});

function addEmptyRow() {
  // addRow() 사용하면 pagination, rownum 자동으로 update
  // tabulator.value.addRow({});

  // 아래처럼 redraw해도 rownum이 자동으로 안됨
  tableData.push({});
  refreshPagination();
  // tabulator.value.redraw();
}
function addRows() {
  for (let inx = 0; inx < 5; inx++) {
    // tabulator.value.addRow({ name: "name" + inx, age: inx + 10 });
    tableData.push({ name: "name" + inx, age: inx + 10 });
  }
}
function deleteRows() {
  tableData.pop();
}

function getData() {
  axios.get("/api/users").then((res) => {
    // rows.value.splice(0);
    // for (let inx = 0; inx < res.data.length; inx++)
    //   rows.value.push(res.data[inx]);

    // tableData = res.data;

    tableData.splice(0);
    for (let inx = 0; inx < res.data.length; inx++)
      tableData.push(res.data[inx]);

    refreshPagination();
  });
}

const refreshPagination = () => {
  // tableData를 toRaw로 감싸니까 제대로 동작함

  // Get the total number of rows
  const totalRows = tableData.length;

  // Get the pagination component
  const counterElement = table.value.querySelector(".tabulator-page-counter");
  if (counterElement) {
    const pageSize = tabulator.value.getPageSize();
    const currentPage = tabulator.value.getPage();
    const startRow = (currentPage - 1) * pageSize + 1;
    const endRow = Math.min(currentPage * pageSize, totalRows);
    counterElement.textContent = `Displaying rows ${startRow} to ${endRow} of ${totalRows}`;
  }
};
</script>

<template>
  <div ref="table"></div>
  <q-btn @click="addEmptyRow">추가</q-btn>
  <q-btn @click="addRows">데이터추가</q-btn>
  <q-btn @click="deleteRows">삭제</q-btn>
  <br />
  <q-btn @click="getData">axios</q-btn>
</template>

<style>
@import "~/tabulator-tables/dist/css/tabulator.min.css";
</style>
