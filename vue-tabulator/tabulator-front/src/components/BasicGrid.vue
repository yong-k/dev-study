<script setup>
import { ref, onMounted, toRaw, watch } from "vue";
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
    data: toRaw(props.rows),
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
      return (
        currentRow + "-" + (currentRow + pageSize - 1) + " of " + totalRows
      );
    },
    footerElement: props.footer
      ? `<div></div><div class='grid-footer'>1-${totalRows.value} of ${totalRows.value}</div>`
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

// watch(
//   () => props.rows,
//   (newRows) => {
//     tabulator.value.setData(newRows).then(() => {
//       console.log("new");
//       updateFooter();
//       // tabulator.value.redraw();
//     });
//   }
// );

function setData(newData) {
  tabulator.value
    .setData(newData)
    .then(() => {
      console.log("Data successfully set.");
      if (props.footer) updateFooter();
    })
    .catch((error) => {
      console.error("Error setting data:", error);
    });
}

function addRows(newRows) {
  // 마지막 페이지로 이동
  const endPage = tabulator.value.getPageMax();
  tabulator.value.setPage(endPage);

  const pageSize = tabulator.value.getPageSize();
  const rowCountOfEndPage = tabulator.value.getRows().length % pageSize;
  let createPageFlag = false;

  // 마지막 페이지 가득 찼으면 새 페이지 생성
  if (rowCountOfEndPage == 0) {
    // 새로운 페이지 만드는 메서드 없음
    // 빈 행을 추가하여 새로운 페이지 생성하고, 나중에 제거
    createPageFlag = true;
    tabulator.value.addRow({ id: "create-page-row" });
    tabulator.value.setPage(endPage + 1);
  }
  tabulator.value.addData(newRows);
  if (createPageFlag) tabulator.value.deleteRow("create-page-row");
  if (props.footer) updateFooter();
}

function deleteRows(idsToDelete) {
  idsToDelete.forEach((id) => {
    if (tabulator.value.getRow(id)) {
      tabulator.value.deleteRow(id);
    }
    tabulator.value.deselectRow();

    if (props.footer) {
      updateFooter();
    }
  });
}

defineExpose({
  setData,
  addRows,
  deleteRows,
});
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

/* checkbox size */
// .tabulator-header input[type="checkbox"] {
//   width: 20px;
//   height: 20px;
// }
// .tabulator-cell input[type="checkbox"] {
//   width: 20px;
//   height: 20px;
// }
</style>
