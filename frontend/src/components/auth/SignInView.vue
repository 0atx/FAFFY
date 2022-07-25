<!--
작성자 : 박윤하
로그인 페이지
-->
<template>
  <v-container>
    <img
      class="mt-10"
      src="@/assets/images/faffy_logo_big.png"
      alt="faffy logo"
    >
    <v-form
      ref="form"
      id="signIn"
      @submit.prevent="requestSignIn"
    >
      <!-- 이메일 입력 -->
      <v-text-field
        v-model="form.email"
        type="email"
        label="이메일"
        required
        @keydown.enter="onInputKeyword"
        />

      <!-- 비밀번호 입력 -->
      <v-text-field
        v-model="form.password"
        :append-icon="type ? 'mdi-eye' : 'mdi-eye-off'"
        :type="type ? 'text' : 'password'"
        label="비밀번호"
        required
        @click:append="type = !type"
        @keydown.enter="onInputKeyword"
      />

      <dark-button
        :btnValue="signInValue"
        @click="requestSignIn"
      />

      <!-- 회원가입 및 비밀번호 찾기  -->
      <div id="route" class="mt-4">
        <div
          type="button"
          @click="goTo"
        >
          회원가입
        </div>
        <div
          type="button"
          @click="findPassword"
        >
          비밀번호 찾기
        </div>
      </div>

      <!-- 소셜 로그인 -->
      <hr>

      <div id="social">
        <!-- 네이버 로그인 -->
        <v-btn
          fab
          elevation="0"
          class="overflow-hidden"
        >
          <img
            src="@/assets/images/naver_login.png"
            alt=""
            style="width:60px; height:60px;"
          >
        </v-btn>

        <!-- 구글 로그인 -->
        <v-btn
          fab
          elevation="0"
          class="overflow-hidden"
        >
          <img
            src="@/assets/images/google_login.png"
            alt=""
            style="width:80px; height:80px;"
          >
        </v-btn>
      </div>
    </v-form>
  </v-container>
</template>

<script>
import DarkButton from '@/components/common/DarkButton.vue'

export default {
  name: "SignIn",
  components: {
    DarkButton
  },
  data() {
      return {
        form: {
          email: '',
          password: '',
        },

        type: false,

        signInValue: '로그인'
      }
  },

  methods: {
    async save() {
    this.$refs.form.validate();
    await this.$nextTick();
    if(!this.valid) return;
    console.log(this.form);
    },
    goTo() {
      this.$router.push({ name: "sign-up" })
    },
    requestSignIn() {
      console.log("로그인 기능 구현해야 합니다.");
    },
    findPassword() {
      console.log("모달 나와야합니다.");
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
.container {
  background-color: white;
  padding: 5%;
}

#signIn {
  width: 30%;
  margin: 0 auto;
}

#route {
  display: flex;
  justify-content: space-between;
  color: #0c0f66;
}

hr {
  border-top: 2px solid #0c0f66;
  text-align: center;
  margin-top: 40px;
  margin-bottom: 30px;
}

hr:after {
  content: '소셜 로그인';
  display: inline-block;
  position: relative;
  top: -13px;
  padding: 0 10px;
  background: #fff;
  color: #0c0f66;
  font-size: 16px;
}

#signInBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}

#social {
  display: flex;
  justify-content: space-around;
}
</style>
