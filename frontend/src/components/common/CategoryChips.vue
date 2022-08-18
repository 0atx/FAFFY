<!--
작성자 : 박윤하
카테고리 칩
-->
<template>
  <v-chip
    id="categoryChips"
  >
    {{ category }}
  </v-chip>
</template>

<script>
export default {
    name: 'CategoryChips',
    props: {
      category: {
        type: String
      }
    },
}
</script>

<style scoped>
#categoryChips {
  background-color: #0c0f66;
  color: #fff;
  pointer-events: none;
}
</style>
