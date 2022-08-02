<!--
작성자 : 박윤하
로그인 페이지
-->
<template>
  <v-container>
    <v-form ref="form" id="signIn" @submit.prevent="requestSignIn">
      <img src="@/assets/images/faffy_logo_big.png" alt="faffy logo" />
      <!-- 이메일 입력 -->
      <v-text-field
        v-model="form.email"
        type="email"
        label="이메일"
        required
        color="#0c0f66"
        @keydown.enter="onInputKeyword"
      />

      <!-- 비밀번호 입력 -->
      <v-text-field
        v-model="form.password"
        :append-icon="type ? 'mdi-eye' : 'mdi-eye-off'"
        :type="type ? 'text' : 'password'"
        label="비밀번호"
        required
        color="#0c0f66"
        @click:append="type = !type"
        @keydown.enter="onInputKeyword"
      />

      <dark-button :btnValue="signInValue" @click="requestSignIn" />

      <!-- 회원가입 및 비밀번호 찾기  -->
      <div id="route" class="mt-4">
        <div type="button" @click="goTo">회원가입</div>

        <v-dialog
          v-model="dialog"
          width="500"
        >
          <template v-slot:activator="{ on, attrs }">
            <div type="button"
              v-bind="attrs"
              v-on="on"
            >
              비밀번호 찾기
            </div>
          </template>
          <v-card>
            <v-card-title id="dialogTitle">
              <img class="ml-5" src="@/assets/images/faffy_logo_big.png" alt="faffy logo" />
            </v-card-title>

            <v-card-text>
              <v-form ref="form" @submit.prevent="findPassword">
                <!-- 이메일 입력 -->
                <v-text-field
                  v-model="find.email"
                  type="email"
                  label="이메일"
                  required
                  color="#0c0f66"
                  @keydown.enter="onInputKeyword"
                />

                <!-- 이름 입력 -->
                <v-text-field
                  v-model="find.name"
                  type="text"
                  label="이름"
                  required
                  color="#0c0f66"
                  @keydown.enter="onInputKeyword"
                />

                <dark-button class="mt-4" :btnValue="findValue" @click="findPassword" />
              </v-form>
              <v-btn
                id="closeBtn"
                class="mt-4 mb-2"
                block
                rounded
                elevation="0"
                @click="dialog = false"
              >닫기</v-btn>
            </v-card-text>
          </v-card>
        </v-dialog>
      </div>

      <!-- 소셜 로그인 -->
      <hr />

      <div id="social">
        <!-- 네이버 로그인 -->
        <v-btn fab elevation="0" class="overflow-hidden">
          <img
            src="@/assets/images/naver_login.png"
            alt=""
            style="width: 60px; height: 60px"
          />
        </v-btn>

        <!-- 구글 로그인 -->
        <v-btn fab elevation="0" class="overflow-hidden">
          <img
            src="@/assets/images/google_login.png"
            alt=""
            style="width: 80px; height: 80px"
          />
        </v-btn>
      </div>
    </v-form>
  </v-container>
</template>

<script>
import DarkButton from "@/components/common/DarkButton.vue";
import { mapMutations } from "vuex";
import { auth } from "@/api/auth.js";
const authStore = "authStore";

export default {
  name: "SignIn",
  components: {
    DarkButton,
  },
  data() {
    return {
      form: {
        email: "",
        password: "",
      },

      find: {
        email: "",
        name: "",
      },

      type: false,

      dialog: false,

      signInValue: "로그인",
      findValue: "비밀번호 찾기",
    };
  },
  computed: {},
  methods: {
    ...mapMutations(authStore, [
      "SET_IS_LOGIN",
      "SET_USER_INFO",
      "SET_IS_LOGIN_ERROR",
    ]),
    async save() {
      this.$refs.form.validate();
      await this.$nextTick();
      if (!this.valid) return;
      console.log(this.form);
    },
    goTo() {
      this.$router.push({ name: "sign-up" });
    },
    findPassword() {
      alert("기능 미구현");
      // 이메일 존재하는지 확인
      // 존재한다면 계정 정보(이름) 가져와서 폼에 입력된 이름이랑 맞는지 확인
      // 이름도 같다면 변경된 비밀번호 이메일로 전송
      // 존재하지 않거나 이름이 다르다면 오류 메세지
      this.dialog = false;
    },
    async requestSignIn() {
      auth.login(
        this.form,
        (response) => {
          console.log("로그인 성공");
          console.log(response.data.content["token"]);
          console.log(response.data.content["user"]);

          const accessToken = response.data.content["token"];
          sessionStorage.setItem("X-AUTH-TOKEN", accessToken);

          const user = response.data.content["user"];
          this.SET_USER_INFO(user);

          this.$router.push({ name: "main" });
        },
        () => {
          alert("로그인 실패. 나중에 바꿔야함");
        }
      );
    },
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
.container {
  background-color: white;
  padding: 5%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
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
  content: "소셜 로그인";
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
}

#dialogTitle {
  justify-content : center;
}

#closeBtn {
  background-color: #ff7451;
  color: #fff;
}

#social {
  display: flex;
  justify-content: space-around;
}
</style>
