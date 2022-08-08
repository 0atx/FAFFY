<!--
작성자: 류경하
설명: 글 작성 Form
최종 수정일: 2022.08.05
-->
<template>
  <v-container>
    <!--제목-->
    <v-text-field
      label="제목"
      outlined
      v-model="boardForm.title"
      max-lenght="100">
    </v-text-field>
    <!--카테고리 선택-->
    <v-select
      :items="categories"
      outlined
      label="게시판 카테고리"
      v-model="boardForm.category">
    </v-select>
    <!--본문 작성-->
    <v-textarea
      outlined
      name="input-7-4"
      label="본문"
      rows="10"
      v-model="boardForm.content">
    </v-textarea>
    <!--이미지 첨부-->
    <v-file-input
      label="File input"
      filled
      v-model="boardForm.img">
    </v-file-input>
    <v-btn id="submitBtn" @click="submitBoard">{{ action==="create" ? "작성" : "수정" }}</v-btn>
    <v-btn color="error" @click="resetForm">초기화</v-btn>
    <!--글 수정일 때만 표시-->
    <v-btn v-if="action === 'update'" color="warning" @click="moveToDetail">취소</v-btn>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'CreateBoardForm',
  props: {
    board: Object,
    action: String,
  },
  data() {
    return {
      categories: ['자유', '질문', '후기'],
      boardForm: {
        title: this.board.title,
        content: this.board.content,
        category: '',
        img: this.currentImage
      }
    }
  },
  computed: {
    ...mapGetters(['currentImage']),
    boardCategory() {
      if (this.boardForm.category === '자유') {
        return 'Free'
      } else if (this.boardForm.category === '질문') {
        return 'QnA'
      } else if (this.boardForm.category === '후기') {
        return 'Info'
      } else {
        return ''
      }
    },
  },
  methods: {
    ...mapActions(boardStore, ['createBoard', 'updateBoard']),
    // 게시글 작성 초기화
    resetForm() {
      if (this.boardForm.title || this.boardForm.content || this.boardForm.img) {
        if (confirm('작성 중이던 내용을 잃게 됩니다. 정말 초기화 하시겠습니까?')) {
          this.boardForm.title = this.board.title
          this.boardForm.category = ''
          this.boardForm.content = this.board.content
          this.boardForm.img = this.board.img
        }
      } else {
        this.boardForm.category = ''
      }
    },
    // 게시글 작성
    submitBoard() {
      let formData = new FormData()
      formData.append('title', this.boardForm.title)
      formData.append('category', this.boardCategory)
      formData.append('content', this.boardForm.content)
      if (this.boardForm.img != null) formData.append('file', this.boardForm.img)
      for (let v of formData.values()) {
          console.log(v)
      }
      if (this.action==='update') {
        console.log('수정이고 글번호는', this.$route.params.boardNo)
        formData.append('no', this.$route.params.boardNo)
        // for (let v of formData.values()) {
        //   console.log(v)
        // }
        this.updateBoard(formData)
      } else {
        this.createBoard(formData)
      }
    },
    moveToDetail() {
      if (confirm('작업 중인 내용을 중단하고 글로 돌아가시겠습니까?')) {
      this.$router.push({ name: 'board-detail', params: { boardNo: this.$route.params.boardNo }})
    }}
  },
}
</script>

<style scoped>
#submitBtn {
  background-color: #0c0f66;
  color: white;
}
</style>
