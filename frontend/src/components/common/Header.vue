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
      <img
        class="logo"
        alt="faffy logo"
        style="cursor:pointer;"
        src="@/assets/images/faffy_logo.png"
        @click="goTo"
      >

      <v-spacer></v-spacer>

      <!-- 검색 바 -->
      <search-bar
        class="ml-12 pl-6"
      />

      <v-spacer></v-spacer>

      <!-- 로그인 후 메뉴 바 -->
      <v-menu
        offset-y
        v-if="userInfo"
      >

        <template v-slot:activator="{ on, attrs }">
          <v-btn icon
            v-bind="attrs"
            v-on="on"
          >
            <profile-icon-avatar />
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
import ProfileIconAvatar from '@/components/common/ProfileIconAvatar.vue'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {
    SearchBar,
    ProfileIconAvatar
  },
  data() {
      return {
        // 임시, 나중에 computed mapState로 userStore에서 userInfo 받아와야 함
        userInfo: false,
      }
  },
  methods: {
    goTo() {
      // this.$router.push({ name: "main" });
      this.$router.push({ name: "consulting-onair" });
    }
  }
};
</script>

<style scoped>
#header {
  width: 100%;
  border-radius: 0px;
  z-index: 2;
}

#signInBtn {
  background-color: #ff4c20;
  color: #fff;
}

#signUpBtn {
  background-color: #0c0f66;
  color: #fff;
}
</style>
