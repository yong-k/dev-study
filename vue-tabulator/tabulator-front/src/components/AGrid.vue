<script setup>
import { ref, onMounted, toRaw, watch } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables";

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
const emit = defineEmits(["rowClick", "rowSelected", "rowMoved"]);

const table = ref(null);
const tabulator = ref(null);

const totalRows = ref(props.rows.length);

//!!! reactiveData: true 로 생성했을 때 vue쪽의 데이터가 변경되면 vue와 tabulator가 모두 변경되어야 할 것 같지만 그렇지 않음.
// data 필드에 vue가 만든 proxy객체를 넣으면 이후 vue에서 추가 삭제하는 데이터들이 proxy객체에는 반영이 안된다.
// 하지만 특이하게도 tabulator쪽에는 반영이 된다.
// vue쪽에서 변경한 데이터가 양쪽에 정상 반영되게 하려면 toRaw()를 이용해서 원본 데이터를 넣어줘야 한다.
// 이 경우 tabulator에서 변경한 데이터는 vue쪽의 데이터에도 반영이 된다. 하지만 watch, watchEffect 등으로 감지할 수는 없다.
// 따로 이벤트를 받아야 데이터 변경을 즉시 알 수 있다. 즉시 알아야할 필요가 없다면 나중에 getEditedCells() 같은 메소드로 알 수는 있다.

onMounted(() => {
  tabulator.value = new Tabulator(table.value, {
    layout: "fitColumns",
    data: toRaw(props.rows), //!!! toRaw()로 변환해서 넣어야 한다. vue가 생성하는 proxy 객체를 넣으면 grid는 정상인것 처럼 보이지만 proxy가 오동작함. 오히려 toRaw()로 원본 객체를 넣어주면 정상 동작. 버그인듯..
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

watch(
  () => props.rows,
  (newRows) => {
    tabulator.value.setData(newRows).then(() => {
      updateFooter();
      tabulator.value.redraw();
    });
  },
  { deep: true }
);
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
