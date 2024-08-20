<script setup>
import { ref, onMounted, watch } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables";
import * as luxon from "luxon";
import * as XLSX from "xlsx";

window.luxon = luxon;
window.XLSX = XLSX;

/****************************** [Props] ******************************
 * rows: Array - required
 *    - 표시할 row 데이터
 *    Examples) :rows="gridRows"
 *
 * columns: Array - required
 *    - column 정의 (객체 배열)
 *    Examples) :columns="gridColumns"
 *
 * height: String
 *    Examples) '300' '300px' '50em' ..
 *
 * minHeight: String
 *    Examples) '300' '300px' '50em' ..
 *
 * maxHeight: String
 *    Examples) '300' '300px' '50em' ..
 *
 * filters: Array
 *    - 검색 필터 정의 (객체 배열)
 *    - Props
 *      · field: 컬럼명
 *      · type: '=' '!=' 'like' '<=' '>=' '<' '>' 'in' ...
 *      · value: String, Integer, Array ...
 *    Examples) [{ field: "date", type: ">=", value: "2023-05-01" },
 *               { field: "date", type: "<=", value: "2024-05-01" },
 *               { field: "age", type:"<", value: 10 },
 *               { field: "name", type: "like", value: "Steve" }]
 *    참고) https://tabulator.info/docs/6.2/filter#search-data
 *
 * movableColumns: Boolean
 *    - column 좌우 이동
 *
 * movableRows: Boolean
 *    - row 상하 이동
 *
 * pagination: Boolean
 *    - pagination 사용 여부
 *
 * paginationSize: Number
 *    - pagination 사용할 경우, 한 페이지당 표시할 row 수
 *
 * footer: Boolean
 *    - pagination 사용하지 않으면서 table footer 표시하고 싶을 때 사용
 ***********************************************************************/

/****************************** [Events] ******************************
 * @rowClick : (e, row) => void
 *    - row 클릭했을 때 emit
 *    - Parameters
 *      · e   : Event   → JS event object
 *      · row : Object  → 클릭한 row
 *
 * @rowSelected : (selectedRows) => void
 *    - checkbox 선택/해제했을 때 emit
 *    - Parameters
 *      · selectedRows : Object[] → 체크박스가 선택된 모든 row의 배열
 *
 * @rowMoved : (e, row) => void
 *    - row 이동했을 때 emit
 *    - Parameters
 *      · e   : Event   → JS event object
 *      · row : Object  → 이동된 row
 *
 * @cellEdited : (cell) => void
 *    - cell 수정했을 때 emit
 *    - Parameters
 *      · cell : Object → 수정된 cell
 ***********************************************************************/

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
  footer: Boolean,
});

// Events
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
    // pagination 사용하지 않을 때, footer 표시
    footerElement: props.footer
      ? `<div></div><div class='grid-footer'>1-${props.rows.length} of ${props.rows.length}</div>`
      : null,
  });

  // event: row 클릭
  tabulator.value.on("rowClick", (e, row) => {
    emit("rowClick", row.getData());
  });

  // event: checkbox 선택/해제
  tabulator.value.on("rowSelected", updateSelectedRows);
  tabulator.value.on("rowDeselected", updateSelectedRows);

  // event: row 이동
  tabulator.value.on("rowMoved", (e, row) => {
    emit("rowMoved", row.getData());
  });

  // event: cell 수정
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
    tabulator.value.replaceData([...newRows]);

    // footerElement는 자동으로 update 되지 않는다.
    if (props.footer) updateFooter();

    // 페이지 설정
    // tabulator.value.setPage(tabulator.value.getPageMax());
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
