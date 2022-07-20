<!--
작성자 : 박윤하
검색 바
-->
<template>
  <div>
    <v-text-field
      hide-details
      label="Filled"
      placeholder="Search"
      filled
      rounded
      dense
      single-line
      append-icon="mdi-magnify" class="shrink mx-4"
      @keydown.enter="onInputKeyword"
      @keyup.tab="onInputKeyword"
    />
  </div>
</template>

<script>
export default {
    name: 'SearchBar',
}
</script>

<style scoped>
.search-bar {
    width: 84vw !important;
  }
</style>
