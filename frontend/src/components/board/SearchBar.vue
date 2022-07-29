<!--
작성자: 류경하
설명: 게시판 글 검색창
최종 수정일: 2022.07.29
-->
<template>
  <v-container fluid>
    <v-row align="center">
      <!--검색 기준 선택(작성자 or 제목)-->
      <v-col
        class="d-flex"
        cols="2"
      >
        <v-select
          :items="items"
          label="검색"
          outlined
        ></v-select>
      </v-col>

      <!--검색어 입력-->
      <v-col
          cols="8"
        >
          <v-text-field
            v-model="keyword"
            label="검색어를 입력하세요"
            append-icon="mdi-magnify"
          ></v-text-field>
        </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      items: ['작성자', '제목'],
      keyword: ''
    }
  }
}
</script>

<style>

</style>
