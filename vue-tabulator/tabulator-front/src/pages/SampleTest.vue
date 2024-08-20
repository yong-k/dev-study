<script setup>
import { ref } from "vue";
import Grid from "components/SampleGrid.vue";

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

const rows = ref([
  {
    id: "id1",
    date: "2014-10-01",
    name: "홍길동",
    quantity: 50,
    price: 3630700,
  },
  {
    id: "id2",
    date: "2024-01-01",
    name: "Steve",
    quantity: 20,
    price: 630800,
  },
  {
    id: "id3",
    date: "2023-05-07",
    name: "Bill",
    quantity: 100,
    price: 150000,
  },
  {
    id: "id4",
    date: "2014-12-25",
    name: "Steve",
    quantity: 55,
    price: 1000000,
  },
]);

// event: row 클릭
function rowClicked(row) {
  console.log(`${row.name} Clicked !!`);
}

// event: row 이동
function rowMoved(row) {
  console.log("moved row id: " + row.id);
}

// row icon
function searchIcon(cell, formatterParams, onRendered) {
  return "<span class='material-icons'>search</span>";
}

////////////////////////////////// 검색 //////////////////////////////////
// 검색 filter
const id = ref("");
const startDate = ref("");
const endDate = ref("");
const name = ref("");
const filters = ref([]);
function search() {
  // 검색 조건 정의
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
// 검색 filter 초기화
function reset() {
  id.value = "";
  startDate.value = "";
  endDate.value = "";
  name.value = "";
  filters.value = [];
}

////////////////////////////////// 행 추가 //////////////////////////////////
// add: 행 추가
let tid = parseInt(rows.value[rows.value.length - 1].id.replace("id", "")) + 1;
function addEmptyRow() {
  rows.value.push({ id: `id${tid++}` });
}

////////////////////////////////// 체크박스 //////////////////////////////////
// checkbox
const selectedRows = ref([]);
function rowSelected(selected) {
  // console.log("selectedId: " + selected.map((row) => row.id));
  selectedRows.value = selected;
}

////////////////////////////////// 선택 삭제 //////////////////////////////////
// delete: checkbox 선택 항목 삭제
function deleteRows() {
  if (selectedRows.value.length == 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  selectedRows.value.forEach((rowData) => {
    const index = rows.value.findIndex((row) => row.id === rowData.id);
    if (index > -1) {
      rows.value.splice(index, 1);
    }
  });

  // checkbox 선택 초기화
  selectedRows.value = [];
}

////////////////////////////////// cell 수정 //////////////////////////////////
// event: cell 수정
const editedRows = ref([]);
function cellEdited(cell) {
  // console.log("edited content:" + cell.getValue());

  // 수정된 row data
  const rowData = cell.getData();

  // 중복된 id인 row가 있다면 기존 row 제거하고 최신 row로 push
  editedRows.value = editedRows.value.filter((item) => item.id !== rowData.id);
  editedRows.value.push(rowData);
}
</script>

<template>
  <div>
    <div class="q-pa-sm">
      <span>SAMPLE Page</span>
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
      </div>

      <grid
        :rows="rows"
        :columns="columns"
        height="350px"
        :filters="filters"
        movableColumns
        movableRows
        @rowClick="rowClicked"
        @rowSelected="rowSelected"
        @rowMoved="rowMoved"
        @cellEdited="cellEdited"
        :footer="true"
      />
    </div>
  </div>
</template>
