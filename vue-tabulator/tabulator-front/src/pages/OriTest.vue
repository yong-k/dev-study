<script setup>
import { reactive, ref, onMounted, onUnmounted } from "vue";
import AGrid from "components/AGrid.vue";

const columns = [
  //Define Table Columns
  {
    title: "No.(paging O)",
    field: "rownum",
    formatter: function (cell) {
      let pageNumber = cell.getTable().getPage();
      let pageSize = cell.getTable().getPageSize();
      let rowIndex = cell.getRow().getPosition();
      return (pageNumber - 1) * pageSize + rowIndex;
    },
    headerHozAlign: "center",
    hozAlign: "center",
    headerSort: false,
    width: 100,
  },
  {
    title: "No.(paging X)",
    field: "rownum",
    formatter: "rownum",
    headerHozAlign: "center",
    hozAlign: "center",
    headerSort: false,
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
    cssClass: "test1 test2",
  },
  {
    title: "Date",
    field: "date",
    headerHozAlign: "center",
    hozAlign: "center",
    headerSort: false,
    editor: "date", // input, textarea, number, date, time, datetime...
    editorParams: {
      min: "2014-01-01", // the minimum allowed value for the date picker
      max: "2014-12-31", // the maximum allowed value for the date picker
      format: "yyyy-MM-dd", // the format of the date value stored in the cell
      verticalNavigation: "table", //navigate cursor around table without changing the value
      elementAttributes: {
        title: "slide bar to choose option", // custom tooltip
      },
    },
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
    formatter: function (cell) {
      const value = cell.getValue();
      const formattedValue = new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(value);
      return `${formattedValue}`;
    },
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
    id: `#Cust1`,
    date: "2024-01-01",
    name: "Steve",
    country: "USA",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
  {
    id: `#Cust2`,
    date: "2023-05-07",
    name: "Bill",
    country: "USA",
    flag: "usa.png",
    product: "IPad Air",
    color: "Green",
    quantity: 100,
    price: 630000,
  },
  {
    id: "#Cust3",
    date: "2014-10-01",
    name: "홍길동",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 630700,
  },
  {
    id: `#Cust4`,
    date: "2024-01-01",
    name: "Steve",
    country: "USA",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
  {
    id: `#Cust5`,
    date: "2023-05-07",
    name: "Bill",
    country: "USA",
    flag: "usa.png",
    product: "IPad Air",
    color: "Green",
    quantity: 100,
    price: 630000,
  },
  {
    id: "#Cust6",
    date: "2014-10-01",
    name: "홍길동",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 630700,
  },
  {
    id: `#Cust7`,
    date: "2024-01-01",
    name: "Steve",
    country: "USA",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
  {
    id: `#Cust8`,
    date: "2023-05-07",
    name: "Bill",
    country: "USA",
    flag: "usa.png",
    product: "IPad Air",
    color: "Green",
    quantity: 100,
    price: 630000,
  },
  {
    id: "#Cust9",
    date: "2014-10-01",
    name: "홍길동",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 630700,
  },
  {
    id: `#Cust10`,
    date: "2024-08-05",
    name: "Steve",
    country: "Korea",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
  {
    id: `#Cust11`,
    date: "2024-08-05",
    name: "Bill",
    country: "USA",
    flag: "usa.png",
    product: "IPad Air",
    color: "Green",
    quantity: 100,
    price: 630000,
  },
  {
    id: `#Cust12`,
    date: "2023-04-07",
    name: "Bill",
    country: "USA",
    flag: "usa.png",
    product: "IPad Air",
    color: "Green",
    quantity: 100,
    price: 630000,
  },
  {
    id: "#Cust13",
    date: "2024-05-01",
    name: "홍길동",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 630700,
  },
]);

function addRows() {
  for (let inx = 0; inx < 10; inx++) {
    rows.value.push({
      id: `#Cust${rows.value.length + inx}`,
      date: "2024-01-01",
      name: `Steve${inx}`,
      country: "USA",
      product: "IPad Air",
      color: "Green",
      quantity: 20 + inx,
      price: 630800 + inx * 100,
    });
  }
}

function addEmptyRow() {
  rows.value.push({});
}

// click row
function rowClicked(row) {
  console.log(`${row.name} Clicked !!`);
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

const newRows = [
  {
    id: "#Cust0",
    date: "2014-10-01",
    name: "테스트1",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 3630700,
  },
  {
    id: `#Cust1`,
    date: "2024-01-01",
    name: "테스트2",
    country: "USA",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
];

// grid rows update & grid redraw
function setData() {
  rows.value = newRows;
}
</script>

<template>
  <div>
    <div class="q-pa-sm">
      <span>Ori</span>
      <a-grid
        :rows="rows"
        :columns="columns"
        height="500px"
        @rowClick="rowClicked"
        @rowSelected="rowSelected"
        :footer="true"
      />

      <!-- height="500px"
        :filters="filters"
        movableColumns
        movableRows
        @rowClick="rowClicked"
        @rowSelected="rowSelected"
        @rowMoved="rowMoved"
        :footer="true" -->

      <div class="row q-mt-xs">
        <q-btn class="q-mr-xs" label="데이터추가" @click="addRows" />
        <q-btn class="q-mr-xs" label="행 추가" @click="addEmptyRow" />
        <q-btn class="q-mr-xs" label="삭제" @click="deleteRows" />
        <q-btn label="setData & redraw" @click="setData" />
      </div>

      <div class="q-mt-md"></div>
      <!-- <a-grid
        :rows="rows"
        :columns="columns"
        min-height="300px"
        max-height="400px"
        :filters="filters"
        @rowClick="rowClicked"
        @rowSelected="rowSelected"
        @rowMoved="rowMoved"
        pagination
        paginationSize="10"
      /> -->
    </div>
  </div>
</template>

<style lang="scss" scoped>
:deep(.test1) {
  color: $primary;
}
:deep(.test2) {
  font-weight: bold;
}
</style>
