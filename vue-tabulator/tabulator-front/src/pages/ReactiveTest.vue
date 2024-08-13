<script setup>
import { ref } from "vue";
import Grid from "components/ReactiveGrid.vue";

const columns = [
  {
    title: "No.",
    field: "rownum",
    formatter: "rownum",
    headerHozAlign: "center",
    hozAlign: "center",
    width: 100,
  },
  {
    formatter: "rowSelection",
    titleFormatter: "rowSelection",
    hozAlign: "center",
    headerHozAlign: "center",
    headerSort: false,
    width: 20,
  },
  {
    title: "ID",
    field: "id",
    headerHozAlign: "center",
    hozAlign: "center",
    width: 100,
    minWidth: 100,
    maxWidth: 300,
    tooltip: true,
  },
  {
    title: "Date",
    field: "date",
    headerHozAlign: "center",
    hozAlign: "center",
    headerSort: false,
    editor: "date",
  },
  { title: "Name", field: "name", editor: "input" },
  { title: "Country", field: "country", hozAlign: "center" },
  { title: "Product", field: "product", width: 150 },
  { title: "Color", field: "color" },
  {
    title: "Quantity",
    field: "quantity",
    editor: "number",
    validator: ["required", "numeric"],
  },
  {
    title: "Price",
    field: "price",
    formatter: (cell) =>
      new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(cell.getValue()),
  },
];

const rows = ref([
  {
    id: "#Cust0",
    date: "2014-10-01",
    name: "홍길동",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 3630700,
  },
  {
    id: "#Cust1",
    date: "2024-01-01",
    name: "Steve",
    country: "USA",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
]);

let id = 2;
function addEmptyRow() {
  rows.value.push({ id: `#Cust${id++}` });
  // rows.value = [...rows.value, { id: `#Cust${id++}` }];
}

// checkbox
const selectedRows = ref([]);
function rowSelected(selected) {
  console.log("selectedId: " + selected.map((row) => row.id));
  selectedRows.value = selected;
}
function deleteRows() {
  selectedRows.value.forEach((rowData) => {
    const index = rows.value.findIndex((row) => row.id === rowData.id);
    if (index > -1) {
      rows.value.splice(index, 1);
    }
  });
}
</script>

<template>
  <div>
    <div class="q-pa-sm">
      <span>Reactive Page</span>
      <div class="row q-mt-xs">
        <q-btn class="q-mr-xs" label="행 추가" @click="addEmptyRow" />
        <q-btn class="q-mr-xs" label="삭제" @click="deleteRows" />
      </div>
      <div class="q-mt-md"></div>
      <grid
        :rows="rows"
        :columns="columns"
        height="400"
        @rowSelected="rowSelected"
        pagination
        paginationSize="10"
        footer
      />
    </div>
  </div>
</template>
