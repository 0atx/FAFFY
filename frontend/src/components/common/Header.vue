<!--
작성자 : 박윤하
헤더 - 상단 네비게이션 바
-->
<template>
  <v-card id="header" class="overflow-hidden">
    <v-app-bar
      elevate-on-scroll
      color="white"
    >
    <!--scroll-target="#" 이거 줘야할 거 같은데...라우터 뷰 어디인지 받아와서 넣을 수 있나 이거?-->
      <v-spacer></v-spacer>

      <img class="logo" alt="faffy logo" src="@/assets/images/faffy_logo.png">

      <v-spacer></v-spacer>

      <!--<v-form
        id="searchBar"
        ref="form"
      >
        <v-text-field
          v-model="search"
          label="검색"
          required
        ></v-text-field>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-form>-->

      <search-bar
        class="ml-12 pl-6"
      />

      <v-spacer></v-spacer>

      <v-menu
        bottom
        left
        v-if="userInfo"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            icon
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

      <div v-else>
        <v-btn
          id="signInBtn"
          class="mr-1"
          elevation="0"
          to="/auth/sign-in"
        >
          로그인
        </v-btn>

        <v-btn
          id="signUpBtn"
          class="ml-1"
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
