<script setup>
import { ref, onMounted } from "vue";
import Grid from "components/AxiosGrid.vue";
import axios from "axios";

const columns = [
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
    formatter: "rowSelection",
    titleFormatter: "rowSelection",
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

// 초기 data setting
const rows = ref([]);
const tid = ref(); // add - testId variable
onMounted(async () => {
  await axios
    .get("/api/users")
    .then((res) => {
      rows.value = res.data;
    })
    .catch((err) => {
      console.error("Failed to fetch data", err);
    });

  tid.value = rows.value.length + 1;

  // set initial data
  gridRef.value.setData(rows.value);
});

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
// const tid = ref(rows.value.length);
function addEmptyRow() {
  const newRow = [{ id: `Cust${tid.value++}` }];

  // rows.value에 추가
  rows.value = [...rows.value, newRow];

  // Grid에 추가
  gridRef.value.addRows(newRow);
}

////// delete
const selectedRows = ref([]);
function rowSelected(selected) {
  console.log("selectedId: " + selected.map((row) => row.id));
  selectedRows.value = selected;
}
function deleteRows() {
  if (!gridRef.value) return;
  const idList = selectedRows.value.map((row) => row.id);

  // rows.value에서 삭제
  // rows.value = rows.value.filter((row) => !idsToDelete.includes(row.id));

  // Grid에서 삭제
  // gridRef.value.deleteRows(idsToDelete);

  axios.delete("/api/users", { data: idList }).then((res) => {
    // gridRef.value.setData(rows.value);
    gridRef.value.redraw();
  });

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
  editedRows.value = [];
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

    <div class="row q-my-md">
      <div style="flex-grow: 1"></div>
      <q-btn class="q-mr-xs" label="추가" @click="addEmptyRow" />
      <q-btn class="q-mr-xs" label="삭제" @click="deleteRows" />
      <q-btn class="q-mr-xs" label="저장" @click="editRows" />
      <q-btn class="q-mr-xs" label="새로고침" @click="refresh" />
    </div>

    <grid
      ref="gridRef"
      :rows="rows"
      :columns="columns"
      height="600px"
      :filters="filters"
      movableColumns
      movableRows
      @rowClick="rowClicked"
      @rowSelected="rowSelected"
      @rowMoved="rowMoved"
      @cellEdited="cellEdited"
      pagination
      :paginationSize="10"
      :footer="true"
    />
  </div>
</template>
