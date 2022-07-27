<!--
작성자 : 박윤하
프로필 뷰 - 우측 세부 프로필 정보
-->
<template>
  <div id="profileDetail">
    <div id="info" class="mt-4">
      <p class="text-h6" style="font-weight: 600">정보</p>
      <hr>
    </div>
    <div id="history">
      <p class="text-h6" style="font-weight: 600">방송 기록
        <v-btn
          class="mx-2 pb-1"
          elevation="0"
          icon
          small
          color="gray"
        >
          <v-icon>
            mdi-plus
          </v-icon>
        </v-btn>
      </p>
      <hr>
    </div>
    <div id="board">
      <p class="text-h6" style="font-weight: 600">게시글
        <v-btn
          class="mx-2 pb-1"
          elevation="0"
          icon
          small
          color="gray"
        >
          <v-icon>
            mdi-chevron-right
          </v-icon>
        </v-btn>
      </p>
      <hr>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfileDetailView",
  data() {
      return {

      }
  },
  metaInfo () {
    return {
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' }
      ]
    }
  },
};
</script>

<style scoped>
#profileDetail {
  background-color: rgb(218, 77, 77);
  padding: 5%;
  text-align: left;
}

#profileDetail div {
  height: 300px;
}

#info {

}

#history {

}

#board {

}
</style>
