<!--
  작성자: 류경하
  설명: 댓글 작성
  최종 수정일: 2022.08.03
-->
<template>
  <v-container>
    <v-row>
      <v-col cols="8">
        <v-textarea
          clearable
          clear-icon="mdi-close-circle"
          label="Text"
          value="This is clearable text.">
        </v-textarea>
      </v-col>
      <v-col cols="4">
        <v-btn color="warning" label="등 록"></v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'CommentForm',
}
</script>

<style>

</style>
