<!--
작성자 : 박윤하
검색 바
-->
<template>
  <div id="searchBar">
    <v-text-field
      hide-details
      label="검색"
      placeholder="검색"
      color="#0c0f66"
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
#searchBar {
    width: 30vw;
  }
</style>
