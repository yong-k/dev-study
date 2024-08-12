<script setup>
import { ref, onMounted, watch, toRaw, toRef } from "vue";
import { TabulatorFull as Tabulator } from "tabulator-tables";

const props = defineProps({
  rows: { type: Array, required: true },
  columns: { type: Array, required: true },
  height: String,
  pagination: Boolean,
  paginationSize: { type: Number, default: 30 },
  footer: { type: Boolean, default: false },
});
const emit = defineEmits(["rowSelected"]);

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

  // Event listeners
  tabulator.value.on("rowSelected", updateSelectedRows);
  tabulator.value.on("rowDeselected", updateSelectedRows);
});

// checkbox 선택된 모든 행의 데이터 가져오기
function updateSelectedRows() {
  emit("rowSelected", tabulator.value.getSelectedData());
}

watch(
  () => props.rows,
  (newRows) => {
    console.log("watch newRows");
    if (props.footer) updateFooter();
    // tabulator.value.setData(newRows);
  },
  { deep: true }
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
