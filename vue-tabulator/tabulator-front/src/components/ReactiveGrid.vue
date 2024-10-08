<script setup>
import { ref, onMounted, watch, toRaw, toRef, onUpdated } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables";
import * as luxon from "luxon";
import * as XLSX from "xlsx";

window.luxon = luxon;
window.XLSX = XLSX;

const props = defineProps({
  rows: { type: Array, required: true },
  columns: { type: Array, required: true },
  height: String, // string, px, %, ...
  minHeight: String,
  maxHeight: String,
  filters: Array,
  movableColumns: Boolean,
  movableRows: Boolean,
  pagination: Boolean,
  paginationSize: { type: Number, default: 30 },
  footer: { type: Boolean, default: false },
});
const emit = defineEmits(["rowClick", "rowSelected", "rowMoved", "cellEdited"]);

const table = ref(null);
const tabulator = ref(null);

const totalRows = ref(props.rows.length);

onMounted(() => {
  tabulator.value = new Tabulator(table.value, {
    layout: "fitColumns",
    data: [...props.rows],
    columns: props.columns,
    reactiveData: true,
    height: props.height,
    minHeight: props.minHeight,
    maxHeight: props.maxHeight,
    movableColumns: props.movableColumns,
    movableRows: props.movableRows,
    placeholder: "검색 결과가 없습니다.",
    pagination: props.pagination,
    paginationSize: props.paginationSize,
    paginationSizeSelector: true,
    paginationCounter: function (
      pageSize,
      currentRow,
      currentPage,
      totalRows,
      totalPages
    ) {
      return `${currentRow}-${currentRow + pageSize - 1} of ${totalRows}`;
    },
    footerElement: props.footer
      ? `<div></div><div class='grid-footer'>footer 1-${props.rows.length} of ${props.rows.length} || </div>`
      : null,
  });

  // event: row click
  tabulator.value.on("rowClick", (e, row) => {
    emit("rowClick", row.getData());
  });

  // event: checkbox select
  tabulator.value.on("rowSelected", updateSelectedRows);
  tabulator.value.on("rowDeselected", updateSelectedRows);

  // event: row move
  tabulator.value.on("rowMoved", (row) => {
    emit("rowMoved", row.getData());
  });

  // event: cell edited
  tabulator.value.on("cellEdited", function (cell) {
    emit("cellEdited", cell);
  });
});

// checkbox 선택된 모든 행의 데이터 가져오기
function updateSelectedRows() {
  emit("rowSelected", tabulator.value.getSelectedData());
}

// rows 변경 감지
watch(
  () => props.rows,
  (newRows) => {
    console.log("watch change");
    tabulator.value.setData([...newRows]);
    if (props.footer) updateFooter();
    tabulator.value.setPage(tabulator.value.getPageMax());
  },
  { deep: true }
);

// 검색 filter
watch(
  () => props.filters,
  (newFilters) => {
    tabulator.value.setFilter(newFilters);
  }
);

function updateFooter() {
  const footer = document.querySelector(".grid-footer");
  if (footer) {
    totalRows.value = tabulator.value.getDataCount();
    footer.innerHTML = `1-${totalRows.value} of ${totalRows.value}`;
  }
}
</script>

<template>
  <div ref="table"></div>
</template>

<style>
@import "~/tabulator-tables/dist/css/tabulator.min.css";
</style>

<style lang="scss">
.tabulator
  .tabulator-header
  .tabulator-col.tabulator-sortable
  .tabulator-col-title {
  padding-right: 17px;
}

.grid-footer {
  font-weight: normal;
}
</style>
