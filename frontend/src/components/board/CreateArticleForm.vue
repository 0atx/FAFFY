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
      value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
      v-model="content">
    </v-textarea>
    <!--이미지 첨부-->
    <v-file-input
      label="File input"
      filled
      v-model="img">
    </v-file-input>
    <v-text-field
      label="해시태그"
      placeholder="Placeholder"
      outlined
      v-model="hashtag">
    </v-text-field>
    <v-btn color="success" @click="submitArticle">작성</v-btn>
    <v-btn color="warning" @click="resetForm">초기화</v-btn>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'CreateArticleForm',
  data() {
    return {
      categories: ['자유', '질문', '후기'],
      title: '',
      category: '',
      content: '',
      img: null,
      hashtag: '',
      token: localStorage.getItem('token')
    }
  },
  methods: {
    ...mapActions(boardStore, ['createArticle']),
    resetForm() {
      this.title = ''
      this.category = ''
      this.content = ''
      this.img = null
      this.hashtag = ''
    },
    submitArticle() {
      const articleForm = {
        'title': this.title,
        'category': this.category,
        'content': this.content,
        'img': this.img
      }

      console.log('article', articleForm)
      this.createArticle(articleForm)
    }
  }
}
</script>

<style>

</style>
