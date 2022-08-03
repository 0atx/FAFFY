<!--
작성자 : 박윤하
프로필 뷰 - 우측 세부 프로필 정보
-->
<template>
  <div id="profileDetail">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">Profile</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600">정보</p>
        <hr />
        <div id="info">
          {{userProfile.info}}
        </div>
      </div>
      <div id="content">
        <p class="text-h6" style="font-weight: 600">
          방송 기록
          <v-btn
            id="moreBtn"
            class="mx-2 mb-1"
            elevation="0"
            icon
            small
            color="gray"
            to="/profile/1/history"
          >
            <v-icon> mdi-plus </v-icon>
          </v-btn>
        </p>
        <hr />
        <div id="history">
          방송 기록 리스트 부분입니다. 추후 방송 기록 리스트 컴포넌트 구현 후
          추가할 예정입니다. <br />ex) 카테고리(참여, 진행) | 방송 제목 ㅁ사진
          갯수 or 방송 일자
        </div>
      </div>
      <div id="content">
        <p class="text-h6" style="font-weight: 600">
          게시글
          <v-btn
            id="moreBtn"
            class="mx-2 mb-1"
            elevation="0"
            icon
            small
            color="gray"
            to="/profile/1/user-board"
          >
            <v-icon> mdi-plus </v-icon>
          </v-btn>
        </p>
        <hr />
        <div id="board">
          게시글 리스트 부분입니다. 추후 게시글 리스트 컴포넌트 구현 후
          추가할 예정입니다. <br />ex) 카테고리(Q&A, 자유) | 게시글 제목 ㅁ댓글
          갯수 or 작성 일자
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex"
const profileStore = "profileStore"
export default {
  name: "ProfileDetailView",
    computed: {
    ...mapState(profileStore,["userProfile"]),
  },
  data() {
    return {};
  },
  metaInfo() {
    return {
      meta: [
        { charset: "utf-8" },
        { name: "viewport", content: "width=device-width, initial-scale=1" },
      ],
    };
  },
};
</script>

<style scoped>
#profileDetail {
  background-color: #fff;
  padding: 5%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: flex-start;
  cursor: default;
}

#profileDetail div {
  width: 100%;
}

#content {
  height: 300px;
  background-color: #fff;
  padding: 2%;
}

#info {
  height: 200px;
}

#history {
  height: 300px;
}

#board {
  height: 300px;
}

#moreBtn::before {
  background-color: transparent;
}

#moreBtn i:hover {
  transform: scale(1.5);
}
</style>
