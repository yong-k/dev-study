<script setup>
import { ref } from "vue";
import TabulatorGrid from "components/TabulatorGrid.vue";
import axios from "axios";

const columns = [
  {
    title: "No.",
    field: "rownum",
    formatter: "rownum",
    width: 50,
  },
  {
    formatter: "rowSelection",
    titleFormatter: "rowSelection",
    headerSort: false,
    width: 20,
  },
  {
    title: "Id",
    field: "id",
    sorter: "string",
    width: 200,
  },
  {
    title: "Name",
    field: "name",
    sorter: "string",
    width: 200,
    editor: "input",
  },
  {
    title: "Age",
    field: "age",
    sorter: "number",
    editor: "number",
    hozAlign: "center",
  },
];
const rows = ref([
  // {
  //   id: "id1",
  //   name: "Oli Bob",
  //   age: 12,
  // },
  // {
  //   id: "id2",
  //   name: "Mary May",
  //   age: 1,
  // },
  // {
  //   id: "id3",
  //   name: "Christine Lobowski",
  //   age: 42,
  // },
  // {
  //   id: "id4",
  //   name: "Brendon Philips",
  //   age: 100,
  // },
  // {
  //   id: "id5",
  //   name: "Margret Marmajuke",
  //   age: 16,
  // },
]);

axios.get("/api/users").then((res) => {
  rows.value = res.data;
});

/*
onMounted(() => {

  //update
  tabulator.value.on("rowUpdated", (row) => {
    console.log("start");
    // Get the updated row data
    const updatedData = row.getData();

    // Find the index of the updated row in the original rows array
    const rowIndex = rows.value.findIndex((r) => r.id === updatedData.id);

    // Update the rows array with the new data
    if (rowIndex !== -1) {
      rows.value[rowIndex] = updatedData;
    }
  });
});
*/

//add row to bottom of table on button click
function addRow() {
  rows.value.push({ name: "IM A NEW ROW", progress: 100, gender: "male" });
  console.log(rows.value);
  // tabulator.value.addRow({
  //   name: "IM A NEW ROW",
  //   age: 100,
  // });
}

/*
function addRows() {
  for (let inx = 0; inx < 6; inx++)
    // rows.value.push({
    tabulator.value.addRow({
      name: "IM A NEW ROW" + inx,
      progress: 10 * inx + inx * 10,
      age: inx,
    });
}

function deleteRow() {
  const selectedRows = tabulator.value.getSelectedRows();
  selectedRows.forEach((row) => row.delete());
}

function updateRow() {
  rows.value[0].name = "IVE BEEN UPDATED";
}
  */
</script>

<template>
  <div>
    <button id="reactivity-add" @click="addRow">Add New Row</button>
    <button id="reactivity-delete" @click="deleteRow">Remove Row</button>
    <button id="reactivity-update" @click="updateRow">
      Update First Row Name
    </button>
    <br />
    <button @click="addRows">Add New Rowss</button>
  </div>

  <tabulator-grid :columns="columns" :rows="rows" height="400px" />
</template>
