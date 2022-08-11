<!--
작성자: 류경하
설명: 글 작성 Form
최종 수정일: 2022.08.11
최종 수정자: 박윤하
-->
<template>
  <v-container class="ml-2">
    <div>
      <div class="mb-4">
        <router-link to="/board" class="text-h6" style="margin-left:10%; font-weight: 600; color:black; text-decoration: none;"><v-icon class="mb-1" color="black" size="20"> mdi-arrow-left </v-icon> 게시판 목록</router-link>
      </div>

      <v-form ref="form" style="width:80%; margin:0 auto">
        <v-subheader class="pa-0">
            카테고리
        </v-subheader>
        <v-tabs color="transparent" class="mb-6">
          <v-tab
            class="mr-4"
            style="height:42px;"
            :id="boardForm.category === '자유' ? 'selectedBtn' : 'selectBtn'"
            @click="boardForm.category = '자유'"
            >자유</v-tab
          >
          <v-tab
            class="mr-4"
            style="height:42px;"
            :id="boardForm.category === '질문' ? 'selectedBtn' : 'selectBtn'"
            @click="boardForm.category = '질문'"
            >질문</v-tab
          >
          <v-tab
            style="height:42px;"
            :id="boardForm.category === '정보' ? 'selectedBtn' : 'selectBtn'"
            @click="boardForm.category = '정보'"
            >정보</v-tab
          >
        </v-tabs>

        <!--제목-->
        <v-text-field
          label="제목"
          outlined
          color="#0c0f66"
          v-model="boardForm.title"
          max-lenght="100">
        </v-text-field>

        <!--본문 작성-->
        <v-textarea
          outlined
          color="#0c0f66"
          name="input-7-4"
          label="본문"
          rows="10"
          no-resize
          v-model="boardForm.content">
        </v-textarea>
        <!--이미지 첨부-->
        <v-file-input
          v-model="boardForm.img"
          accept="image/*"
          label="이미지 파일 첨부"
          color="#0c0f66">
        </v-file-input>

        <div style="text-align:right;">
        <!--글 수정일 때만 표시-->
        <v-btn class="mr-4" style="height:42px; width:90px;" elevation="0" v-if="action === 'update'" id="cancelBtn" @click="moveToDetail">취소</v-btn>
        <v-btn class="mr-4" style="height:42px; width:90px;" elevation="0" @click="resetForm">초기화</v-btn>
        <v-btn style="height:42px; width:90px;" elevation="0" id="submitBtn" @click="submitBoard">{{ action==="create" ? "작성" : "수정" }}</v-btn>
        </div>
      </v-form>

    </div>
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
      categories: ['자유', '질문', '정보'],
      boardForm: {
        title: this.board.title,
        content: this.board.content,
        category: '자유',
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
      } else if (this.boardForm.category === '정보') {
        return 'Info'
      } else {
        return ''
      }
    },
  },
  methods: {
    ...mapActions(boardStore, ['createBoard', 'updateBoard']),
    // 게시글 작성 초기화
    async resetForm() {
      if (this.boardForm.title || this.boardForm.content || this.boardForm.img) {
        const res = await this.$dialog.confirm({
          text: '<br>작성 중이던 내용을 잃게 됩니다.<br> 정말 초기화 하시겠습니까?',
          icon: true,
          actions: {
            false : {
              text: '취소', color: '#ff7451'
            },
            true : {
              text: '확인', color: '#0c0f66'
            },
          }
        });
        if (res) {
          this.$refs.form.reset()
        }
      } else {
        this.boardForm.category = '자유'
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
    async moveToDetail() {
      const res = await this.$dialog.confirm({
          text: '<br>작업 중인 내용을 중단하고 글로 돌아가시겠습니까?',
          icon: true,
          actions: {
            false : {
              text: '취소', color: '#ff7451'
            },
            true : {
              text: '확인', color: '#0c0f66'
            },
          }
        });
      if (res) {
      this.$router.push({ name: 'board-detail', params: { boardNo: this.$route.params.boardNo }})
    }}
  },
}
</script>

<style scoped>
.container {
  padding-bottom: 5%;
  text-align: left;
}

#cancelBtn {
  background-color: #ff7451;
  color: white;
}

#submitBtn {
  background-color: #0c0f66;
  color: white;
}

#createBoard {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#selectBtn {
  background-color: #fff;
  border: 1px solid #9e9e9e;
  border-radius: 5px;
  color: #828282;
  font-size: 16px;
}

#selectBtn::before {
  border-radius: 5px;
  color: #f0f0f0;
  font-size: 16px;
}

#selectedBtn {
  background-color: #ff7451;
  border-radius: 5px;
  color: #fff;
  font-size: 16px;
}

::v-deep ::-webkit-scrollbar {
  width: 0;
  background: transparent;
}
</style>
