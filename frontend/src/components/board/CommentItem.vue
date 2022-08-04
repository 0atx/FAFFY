<!--
  작성자: 류경하
  설명: 각각의 댓글
  최종 수정일: 2022.08.04
-->
<template>
  <v-container fluid>
    <v-card
      outlined
      class="mx-auto text-left"
    >
    <!-- 댓글 내용 -->
      <v-card-text>
        <div>{{ comment.writer.nickname }}</div>
        <div class="text--primary">
          {{ comment.content}}
        </div>
      </v-card-text>
    <!-- 수정, 삭제 버튼 -->
      <v-card-actions>
        <v-btn
          text
          color="deep-purple accent-2"
        >
          수정
        </v-btn>
        <v-btn
          text
          color="error accent-2"
        >
          삭제
        </v-btn>
        <p>작성일: {{ comment.datetime }}</p>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: 'CommentItem',
  props: {
    comment: Object,
  }
}
</script>

<style>

</style>
