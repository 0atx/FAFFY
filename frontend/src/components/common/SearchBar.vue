<!--
작성자 : 박윤하
검색 바
 -->
<template>
  <div>
    <v-text-field
      v-model="query"
      :label="label"
      filled
      rounded
      dense
      solo
      :class="{ 'search-bar': isDetail, 'mx-3': isDetail, 'mx-4': !isDetail }"
      class="mt-5"
      prepend-inner-icon="mdi-magnify"
      type="text"
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
