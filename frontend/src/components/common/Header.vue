<!--
작성자 : 박윤하
헤더 - 상단 네비게이션 바
-->
<template>
  <v-card id="header" class="overflow-hidden">
    <v-app-bar
      style="padding: 0px 10% 0px 10%;"
      color="white"
    >

      <!-- 로고 -->
      <img
        class="logo"
        alt="faffy logo"
        style="cursor:pointer; padding-bottom:2px;"
        src="@/assets/images/faffy_logo.png"
        @click="toMain"
      >

      <v-spacer></v-spacer>

      <!-- 검색 바 -->
      <search-bar
        style="margin-left:9%"
      />

      <v-spacer></v-spacer>

      <!-- 로그인 후 메뉴 바 -->
      <div style="display:flex;align-items:center" v-if="loginUser">
        <v-btn
          style="font-size:16px;width:80px"
          class="mr-1"
          id="consultBtn"
          rounded
          elevation="0"
          :ripple="false"
          @click="toReady"
        >
          방송 시작
        </v-btn>

        <v-btn
          style="font-size:16px;width:80px"
          class="mr-1"
          id="signOutBtn"
          rounded
          elevation="0"
          :ripple="false"
          @click="requestSignOut"
        >
          로그아웃
        </v-btn>
        <v-btn
          small
          icon
          size="40"
          elevation="0"
          :ripple="false"
          @click="toMyProfile"
        >
          <profile-icon-avatar class="ml-2"></profile-icon-avatar>
        </v-btn>
      </div>

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


    </v-app-bar>
  </v-card>
</template>

<script>
import SearchBar from '@/components/common/SearchBar.vue'
import ProfileIconAvatar from '@/components/common/ProfileIconAvatar.vue'
import { mapState, mapActions } from "vuex";
// import { auth } from "@/api/auth.js";

const authStore = "authStore";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {
    SearchBar,
    ProfileIconAvatar
  },
    computed: {
    ...mapState(authStore, ["loginUser","isLogin"]),
  },
  methods: {
    ...mapActions(authStore,["logout"]),
    toMain() {
      this.$router.push({ name: "main" });
    },
    toReady() {
      //this.$router.push({ name: "consulting-ready" });
      this.$router.push({ name: "consulting-onair" });
    },
    toMyProfile() {
      this.$router.push("/profile/"+this.loginUser.no).catch(()=>{});
    },
    async requestSignOut() {
      await this.logout();
      if (!this.isLogin) {
        this.$router.push({name:"main"});
      }
    },
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

#consultBtn {
  background-color: #fff;
  color: #0c0f66;
  text-decoration: none;
}

#signOutBtn {
  background-color: #fff;
  color: #ff4c20;
}

.v-btn::before {
  background-color: transparent;
}

</style>
