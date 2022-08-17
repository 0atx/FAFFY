<!--
작성자 : 박윤하
헤더 - 상단 네비게이션 바
-->
<template>
  <v-card id="header" class="overflow-hidden">
    <v-app-bar
      id="appBar"
      color="white"
    >

      <!-- 로고 -->
      <img
        class="logo"
        alt="faffy logo"
        src="@/assets/images/faffy_logo.png"
        @click="toMain"
      >

      <v-btn
          v-if="!isOnAir"
          class="ml-1"
          id="consultBtn"
          rounded
          elevation="0"
          :ripple="false"
          @click="toBoard"
        >
          게시판
        </v-btn>

      <v-spacer></v-spacer>

      <!-- 검색 바 -->
      <search-bar
        id="searchBar"
      />

      <v-spacer></v-spacer>

      <!-- 로그인 후 메뉴 바 -->
      <div style="display:flex;align-items:center" v-if="loginUser">
        <v-btn
          v-if="!isOnAir"
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
          v-else
          class="mr-1"
          id="consultBtn"
          rounded
          elevation="0"
          :ripple="false"
          @click="toReady"
        >
          방송 나가기
        </v-btn>

        <v-btn
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
          <profile-img-avatar id="avatar" class="ml-8" :user_no="loginUser.no" />
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
import ProfileImgAvatar from '@/components/common/ProfileImgAvatar.vue'
import { mapState, mapActions } from "vuex";
// import { auth } from "@/api/auth.js";

const authStore = "authStore";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {
    SearchBar,
    ProfileImgAvatar
  },
  props: {
    isOnAir: Boolean,
  },
  computed: {
    ...mapState(authStore, ["loginUser","isLogin"]),
  },
  methods: {
    ...mapActions(authStore,["logout"]),
    toMain() {
      this.$router.push({ name: "main" }).catch(()=>{});
    },
    toReady() {
      this.$router.push({ name: "consulting-ready" }).catch(()=>{});
      // this.$router.push({ name: "consulting-onair" });
    },
    toBoard() {
      this.$router.push({ name: "board" }).catch(()=>{});
    },
    toMyProfile() {
      this.$router.push("/profile/"+this.loginUser.no).catch(()=>{});
    },
    async requestSignOut() {
      await this.logout();
      if (!this.isLogin) {
        this.$router.push({name:"main"});
        this.$dialog.message.info('로그아웃 되었습니다.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
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

#appBar {
  padding: 0px 10%;
}

.logo {
  cursor:pointer;
  padding-bottom:2px;
}

#searchBar {
  margin-left: 9%;
}

@media (max-width: 600px) {
  img {
    width: 60px;
  }

  #signInBtn {
    width: 40px;
  }

  #signUpBtn {
    width: 40px;
  }

  #consultBtn {
    background-color: #fff;
    color: #0c0f66;
    font-size:14px;
    width:70px !important;
    text-decoration: none;
  }

  #signOutBtn {
    background-color: #fff;
    font-size:14px;
    width:70px !important;
    color: #ff4c20;
  }

  .spacer {
    width: 10px !important;
    flex-grow: 0;
  }

  #searchBar {
    margin-left: 0%;
    width: 100px !important;
  }

  #appBar {
    padding: 0 !important;
  }

  .v-toolbar__content {
    padding: 4px 0px !important;
  }

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
  font-size:16px;
  width:80px;
  text-decoration: none;
}

#signOutBtn {
  background-color: #fff;
  font-size:16px;
  width:80px;
  color: #ff4c20;
}

.v-btn::before {
  background-color: transparent;
}

</style>
