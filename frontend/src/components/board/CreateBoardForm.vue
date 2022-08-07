<!--
작성자: 류경하
설명: 글 작성 Form
최종 수정일: 2022.08.02
-->
<template>
  <v-container>
    <!--제목-->
    <v-text-field
      label="제목"
      outlined
      v-model="title"
      max-lenght="100">
    </v-text-field>
    <!--카테고리 선택-->
    <v-select
      :items="categories"
      outlined
      label="게시판 카테고리"
      v-model="category">
    </v-select>
    <!--본문 작성-->
    <v-textarea
      outlined
      name="input-7-4"
      label="본문"
      rows="10"
      v-model="content">
    </v-textarea>
    <!--이미지 첨부-->
    <v-file-input
      label="File input"
      filled
      multiple
      v-model="img">
    </v-file-input>
    <v-btn color="success" @click="submitBoard">작성</v-btn>
    <v-btn color="warning" @click="resetForm">초기화</v-btn>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'CreateBoardForm',
  data() {
    return {
      categories: ['자유', '질문', '후기'],
      title: '',
      category: '',
      content: '',
      img: null,
    }
  },
  computed: {
    boardCategory() {
      if (this.category === '자유') {
        return 'Free'
      } else if (this.category === '질문') {
        return 'QnA'
      } else {
        return 'Info'
      }
    }
  },
  methods: {
    ...mapActions(boardStore, ['createBoard']),
    // 게시글 작성 초기화
    resetForm() {
      if (this.title || this.content || this.img) {
        if (confirm('작성 중이던 내용을 잃게 됩니다. 정말 초기화 하시겠습니까?')) {
          this.title = ''
          this.category = ''
          this.content = ''
          this.img = null
        }
      } else {
        this.category = ''
      }
    },
    // 게시글 작성
    submitBoard() {
      // const boardForm = {
      //   'title': this.title,
      //   'category': this.boardCategory,
      //   'content': this.content,
      //   'img': this.img
      // }
      let boardForm = new FormData();
      boardForm.append("title", this.title);
      boardForm.append("category", this.boardCategory);
      boardForm.append("content", this.content);
      if(this.img != null) boardForm.append("file", this.img);

      console.log('board', boardForm)
      this.createBoard(boardForm)
    }
  }
}
</script>

<style>

</style>
