<script setup>
import { ref } from "vue";
import Grid from "components/BasicGrid.vue";

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
  { title: "Country", field: "country", hozAlign: "center", editor: "input" },
  { title: "Product", field: "product", editor: "input", width: 150 },
  { title: "Color", field: "color", editor: "input" },
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
  {
    formatter: searchIcon,
    width: 30,
    hozAlign: "center",
    headerSort: false,
    cellClick: function (e, cell) {
      alert("Searching row data for: " + cell.getRow().getData().id);
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

// click row
function rowClicked(row) {
  console.log(`${row.name} Clicked !!`);
}

// move row
function rowMoved(row) {
  console.log("moved row id: " + row.id);
}

// row icon
function searchIcon(cell, formatterParams, onRendered) {
  return "<span class='material-icons'>search</span>";
}

// search filter
const id = ref("");
const startDate = ref("");
const endDate = ref("");
const name = ref("");
const filters = ref([]);
function search() {
  const newFilters = [];
  if (id.value) {
    newFilters.push({ field: "id", type: "like", value: id.value });
  }
  if (startDate.value) {
    newFilters.push({ field: "date", type: ">=", value: startDate.value });
  }
  if (endDate.value) {
    newFilters.push({ field: "date", type: "<=", value: endDate.value });
  }
  if (name.value) {
    newFilters.push({ field: "name", type: "like", value: name.value });
  }
  filters.value = newFilters;
}

const gridRef = ref(null);
////// add
function addRows() {
  const newRows = [];
  for (let inx = 0; inx < 10; inx++) {
    newRows.push({
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

  rows.value = [...rows.value, ...newRows];
  gridRef.value.updateOrAdd(rows.value);
}
function addEmptyRow() {
  // rows.value.push({ id: `#Cust${rows.value.length++}` });
  rows.value = [...rows.value, { id: `#Cust${rows.value.length++}` }];

  gridRef.value.updateOrAdd(rows.value);
}

////// delete
const selectedRows = ref([]);
function rowSelected(selected) {
  // console.log("selectedId: " + selected.map((row) => row.id));
  selectedRows.value = selected;
}
function deleteRows() {
  if (!gridRef.value) return;
  const idsToDelete = selectedRows.value.map((row) => row.id);
  gridRef.value.deleteRows(idsToDelete);
  selectedRows.value = [];
}

////// update
const editedRows = ref([]);
function cellEdited(cell) {
  // console.log("edited content:" + cell.getValue());
  // console.log("edited row's id: " + cell.getData().id);

  const rowData = cell.getData();

  // 중복된 id인 row가 있다면 기존row 제거하고 최신row로 push
  editedRows.value = editedRows.value.filter((item) => item.id !== rowData.id);
  editedRows.value.push(rowData);
}
function editRows() {
  console.log(editedRows.value.forEach((row) => console.log(row.id)));
}

// 데이터 axios로 다시 받아왔다고 치고
const refreshData = [
  {
    id: "#Cust0",
    date: "2014-10-01",
    name: "홍T",
    country: "Korea",
    product: "IPad Air",
    color: "Red",
    quantity: 50,
    price: 3630700,
  },
  {
    id: `#Cust1`,
    date: "2024-01-01",
    name: "ST",
    country: "USA",
    product: "IPad Air",
    color: "Green",
    quantity: 20,
    price: 630800,
  },
];
function refresh() {
  rows.value = refreshData;
  gridRef.value.setData(rows.value);
}
</script>

<template>
  <div class="q-pa-sm">
    <span>BASIC Page</span>
    <div class="row items-center q-gutter-md no-wrap q-mb-sm">
      <span>ID</span>
      <q-input v-model="id" />

      <span>DATE</span>
      <q-input type="date" v-model="startDate" />
      <span>~</span>
      <q-input type="date" v-model="endDate" />

      <span>NAME</span>
      <q-input v-model="name" />

      <q-btn label="조회" @click="search" />
    </div>

    <!-- <grid
      :rows="rows"
      :columns="columns"
      height="500px"
      :filters="filters"
      movableColumns
      movableRows
      @rowClick="rowClicked"
      @rowSelected="rowSelected"
      @rowMoved="rowMoved"
      footer="true"
    /> -->

    <div class="row q-mt-xs">
      <div style="flex-grow: 1"></div>
      <q-btn class="q-mr-xs" label="데이터추가" @click="addRows" />
      <q-btn class="q-mr-xs" label="행 추가" @click="addEmptyRow" />
      <q-btn class="q-mr-xs" label="삭제" @click="deleteRows" />
      <q-btn class="q-mr-xs" label="저장" @click="editRows" />
      <q-btn class="q-mr-xs" label="새로고침" @click="refresh" />
    </div>

    <div class="q-mt-md"></div>
    <grid
      ref="gridRef"
      :rows="rows"
      :columns="columns"
      height="600px"
      :filters="filters"
      movableColumns
      movableRows
      @rowClick="rowClicked"
      @rowMoved="rowMoved"
      @rowSelected="rowSelected"
      @cellEdited="cellEdited"
      pagination
      :paginationSize="10"
      :footer="true"
    />
  </div>
</template>
