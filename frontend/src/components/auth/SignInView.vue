<!--
작성자 : 박윤하
로그인 페이지
-->
<template>
  <v-container>
    <img
      src="@/assets/images/faffy_logo_big.png"
      alt="faffy logo"
    >
    <v-form ref="form" id="signIn">
      <v-text-field
        v-model="credentials.email"
        type="email"
        :rules="emailRules"
        label="이메일을 입력해주세요."
        required
        @keydown.enter="onInputKeyword"
        />

      <v-text-field
        v-model="credentials.password"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :type="showPassword ? 'text' : 'password'"
        :rules="[passwordRules.min]"
        label="비밀번호를 입력해주세요."
        required
        @click:append="showPassword = !showPassword"
        @keydown.enter="onInputKeyword"
      />

      <v-btn
        id="signInBtn"
        class="mt-2"
        block
        elevation="0"
        type="submit"
        @click="requestSignIn"
      >로그인</v-btn>

      <!-- 회원가입 및 비밀번호 찾기  -->
      <div id="route" class="mt-4">
        <div
          type="button"
          @click="moveRegist"
        >
          회원가입
        </div>
        <div
          type="button"
          @click="moveRegist"
        >
          비밀번호 찾기
        </div>
      </div>

      <hr>

      <div id="social">
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
export default {
  name: "SignIn",
  data() {
      return {
        credentials: {
          email: '',
          password: '',
        },

        valid: false,

        email: '',
        emailRules: [
          v => !!v || '이메일을 입력해주세요.',
          v => /.+@.+\..+/.test(v) || '올바른 형식의 이메일을 입력해주세요.',
        ],

        password: '',
        showPassword: false,
        passwordRules: {
          min: v => v.length >= 4 || '올바른 비밀번호를 입력해주세요.',
        }
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
