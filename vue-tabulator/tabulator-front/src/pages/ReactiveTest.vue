<script setup>
import { ref, onMounted } from "vue";
import Grid from "components/ReactiveGrid.vue";
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
    editor: "input",
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

const rows = ref([]);
async function fetchData() {
  await axios
    .get("/api/users")
    .then((res) => {
      rows.value = res.data;
    })
    .catch((err) => {
      console.error("Failed to fetch data", err);
    });
}

const tid = ref(); // add - test id 변수
onMounted(async () => {
  await fetchData();
  tid.value =
    parseInt(rows.value[rows.value.length - 1].id.replace("Cust", "")) + 1;
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
function reset() {
  filters.value = [];
}

////// add empty row
function addEmptyRow() {
  rows.value.push({ id: `Cust${tid.value++}` });
}

////// delete
const selectedRows = ref([]);
function rowSelected(selected) {
  // console.log("selectedId: " + selected.map((row) => row.id));
  selectedRows.value = selected;
}
const deletedRows = ref([]);
function deleteRows() {
  if (selectedRows.value.length == 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  // 화면에서 삭제 + deletedRows 배열에 추가
  selectedRows.value.forEach((rowData) => {
    const index = rows.value.findIndex((row) => row.id === rowData.id);
    if (index > -1) {
      deletedRows.value.push(rowData.id);
      rows.value.splice(index, 1);
    }
  });

  // axios
  // const idList = selectedRows.value.map((row) => row.id);
  // axios
  //   .delete("/api/users", { data: idList })
  //   .then((res) => {
  //     if (res.data > 0) fetchData();
  //     else alert("삭제 오류 msg");
  //     editedRows.value.forEach((row) => console.log(row.name));
  //   })
  //   .catch((err) => {
  //     console.error(err);
  //   });

  // checkbox 선택 초기화
  selectedRows.value = [];

  deletedRows.value.forEach((row) => console.log(row));
}

////// row add/update 감지
const editedRows = ref([]);
function cellEdited(cell) {
  // console.log("edited content:" + cell.getValue());
  // console.log("edited row's id: " + cell.getData().id);

  const rowData = cell.getData();

  // 중복된 id인 row가 있다면 기존row 제거하고 최신row로 push
  editedRows.value = editedRows.value.filter((item) => item.id !== rowData.id);
  editedRows.value.push(rowData);
}

////// save add/update/delete content
function save() {
  if (editedRows.value.length == 0 && deletedRows.value.length == 0) return;

  // row 생성할 때마다 unique id가 부여된다고 가정
  // editedRows와 deletedRows 모두에 있는거 editedRows에서 제거
  editedRows.value = editedRows.value.filter(
    (editedRow) => !deletedRows.value.includes(editedRow.id)
  );

  axios
    .post("/api/users", {
      editedRows: editedRows.value,
      deletedRows: deletedRows.value,
    })
    .then((res) => {
      fetchData();
    })
    .catch((err) => {
      console.error(err);
    });

  editedRows.value = [];
  deletedRows.value = [];
}

function refresh() {
  fetchData();
}
</script>

<template>
  <div>
    <div class="q-pa-sm">
      <span>Reactive Page</span>
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
        <q-btn label="초기화" @click="reset" />
      </div>

      <div class="row q-my-md">
        <div style="flex-grow: 1"></div>
        <q-btn class="q-mr-xs" label="추가" @click="addEmptyRow" />
        <q-btn class="q-mr-xs" label="삭제" @click="deleteRows" />
        <q-btn class="q-mr-xs" label="저장" @click="save" />
        <q-btn class="q-mr-xs" label="새로고침" @click="refresh" />
      </div>

      <grid
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
  </div>
</template>
