<!--
작성자 : 박윤하
헤더 - 상단 네비게이션 바
-->
<template>
  <v-card id="header" class="overflow-hidden">
    <v-app-bar
      color="white"
    >
      <v-spacer></v-spacer>

      <!-- 로고 -->
      <img class="logo" alt="faffy logo" src="@/assets/images/faffy_logo.png">

      <v-spacer></v-spacer>

      <!-- 검색 바 -->
      <search-bar
        class="ml-12 pl-6"
      />

      <v-spacer></v-spacer>

      <!-- 로그인 후 메뉴 바 -->
      <v-menu
        bottom
        left
        v-if="userInfo"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="black"
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item>
            <v-list-item-title>내 프로필</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>방송 시작</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <!-- 로그인 전 메뉴 바 -->
      <div v-else>
        <v-btn
          id="signInBtn"
          class="mr-1"
          rounded
          elevation="0"
          to="/auth/sign-in"
        >
          로그인
        </v-btn>

        <v-btn
          id="signUpBtn"
          class="ml-1"
          rounded
          elevation="0"
          to="/auth/sign-up"
        >
          회원가입
        </v-btn>
      </div>

      <v-spacer></v-spacer>

    </v-app-bar>
  </v-card>
</template>

<script>
import SearchBar from '@/components/common/SearchBar.vue'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {
    SearchBar
  },
  data() {
      return {
        // 임시, 나중에 computed mapState로 userStore에서 userInfo 받아와야 함
        userInfo: false,
      }
  }
};
</script>

<style scoped>
#header {
  position: fixed;
  top: 0;
  width: 100%;
  border-radius: 0px;
  z-index: 2;
}

#signInBtn {
  background-color: #fff;
  color: #0c0f66;
  border: 1px solid #0c0f66;
}

#signUpBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}
</style>
