<!--
작성자 : 박윤하
헤더 - 상단 네비게이션 바
-->
<template>
  <v-card id="header" class="overflow-hidden">
    <v-app-bar
      color="white"
    >
      <img class="logo" alt="faffy logo" src="@/assets/images/faffy_logo.png">

      <v-spacer></v-spacer>

      <v-form
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
      </v-form>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>

      <v-menu
        bottom
        left
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

        <v-list v-if="userInfo">
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

        <v-list v-else>
          <v-list-item to="/signin">
            <v-list-item-title>로그인</v-list-item-title>
          </v-list-item>

          <v-list-item to="/signup">
            <v-list-item-title>회원가입</v-list-item-title>
          </v-list-item>

        </v-list>
      </v-menu>
    </v-app-bar>
  </v-card>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
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
  border-radius: 0px;
}

#searchBar {
    display: flex;
}
</style>
