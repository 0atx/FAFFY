<!--
작성자 : 박윤하
회원가입 페이지
-->
<template>
  <v-container>
    <v-form ref="form" id="signUp" @submit.prevent="requestSignUp">
      <img src="@/assets/images/faffy_logo_big.png" alt="faffy logo" />
      <div id="check">
        <div id="checkInput">
          <!-- 이메일 입력 -->
          <v-text-field
            v-model="form.email"
            type="email"
            :rules="rules.email()"
            label="이메일"
            required
            color="#0c0f66"
            hide-details
            @input="checkingEmail"
            @keydown.enter="onInputKeyword"
            ref="email"
          />
        </div>

        <!-- 이메일 중복 확인 -->
        <v-icon id="checkEmailBtn" class="mt-2" :color="checkEmailIcon ? '#0c0f66' : '#ff4c20'">mdi-check</v-icon>
      </div>

      <!-- 비밀번호 입력 -->
      <v-text-field
        v-model="form.password"
        type="password"
        :rules="rules.password()"
        hint="8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요."
        persistent-hint
        label="비밀번호"
        required
        color="#0c0f66"
        @keydown.enter="onInputKeyword"
      />

      <!-- 비밀번호 확인 -->
      <v-text-field
        v-model="confirmPw"
        type="password"
        :rules="[rules.matchValue(form.password)]"
        hint="비밀번호를 재입력해주세요."
        persistent-hint
        label="비밀번호 확인"
        required
        color="#0c0f66"
        @keydown.enter="onInputKeyword"
      />

      <!-- 이름 입력 -->
      <v-text-field
        v-model="form.name"
        type="text"
        :rules="rules.name()"
        hint="한글 이름을 입력하세요."
        persistent-hint
        label="이름"
        required
        color="#0c0f66"
        @keydown.enter="onInputKeyword"
      />

      <div id="check">
        <div id="checkInput">
          <!-- 별명 입력 -->
          <v-text-field
            v-model="form.nickname"
            type="text"
            :rules="rules.nickname()"
            hint="2~10자 특수문자를 제외한 별명을 입력하세요."
            persistent-hint
            label="별명"
            required
            color="#0c0f66"
            @input="checkingNickname"
            @keydown.enter="onInputKeyword"
            ref="nickname"
          />
        </div>

        <!-- 별명 중복 확인 -->
        <v-icon id="checkNicknameBtn" class="mt-2" :color="checkNicknameIcon ? '#0c0f66' : '#ff4c20'">mdi-check</v-icon>
      </div>

      <v-menu
        ref="menu"
        v-model="menu"
        :close-on-content-click="false"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <!-- 생년월일 입력 -->
          <v-text-field
            v-model="form.birthday"
            :rules="rules.birth()"
            label="생년월일"
            append-icon="mdi-calendar"
            readonly
            color="#0c0f66"
            hide-details
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="form.birthday"
          color="#0c0f66"
          :active-picker.sync="activePicker"
          locale="ko=KR"
          max="2001-01-01"
          min="1922-01-01"
          @change="save"
        ></v-date-picker>
      </v-menu>

      <!-- 성별 입력 -->
      <v-radio-group
        row
        v-model="form.gender"
        label="성별"
        :rules="rules.gender()"
      >
        <v-radio label="남자" value="0" color="#0c0f66"></v-radio>
        <v-radio label="여자" value="1" color="#0c0f66"></v-radio>
      </v-radio-group>

      <!-- 이용약관 -->
      <div id="terms" class="mb-4">
        회원가입 시 FAFFY(패피)가 제공하는 서비스를 모두 이용하실 수 있습니다.
        <v-dialog
          v-model="serviceDialog"
          width="800"
        >
          <template v-slot:activator="{ on, attrs }">
            <div
              id="termsDialog"
              type="button"
              v-bind="attrs"
              v-on="on"
            >
              서비스 이용약관
            </div>
          </template>
          <v-card>
            <v-card-title id="dialogTitle">
              <img class="ml-5" src="@/assets/images/faffy_logo_big.png" alt="faffy logo" />
            </v-card-title>

            <v-card-text>
              <service-terms-text />
              <v-btn
                id="closeBtn"
                class="mt-4 mb-2"
                block
                rounded
                elevation="0"
                @click="serviceDialog = false"
              >닫기</v-btn>
            </v-card-text>
          </v-card>
        </v-dialog>
         및
        <v-dialog
          v-model="privateDialog"
          width="800"
        >
          <template v-slot:activator="{ on, attrs }">
            <div
              id="termsDialog"
              type="button"
              v-bind="attrs"
              v-on="on"
            >
              개인정보 취급 방침
            </div>
          </template>
          <v-card>
            <v-card-title id="dialogTitle">
              <img class="ml-5" src="@/assets/images/faffy_logo_big.png" alt="faffy logo" />
            </v-card-title>

            <v-card-text>
              <private-terms-text />
              <v-btn
                id="closeBtn"
                class="mt-4 mb-2"
                block
                rounded
                elevation="0"
                @click="privateDialog = false"
              >닫기</v-btn>
            </v-card-text>
          </v-card>
        </v-dialog>
        에 동의합니다.
      </div>

      <dark-button :btnValue="signUpValue" @click="requestSignUp" />
    </v-form>
  </v-container>
</template>

<script>
import DarkButton from "@/components/common/DarkButton.vue";
import validateRules from "@/utils/validateRules.js";
import ServiceTermsText from './ServiceTermsText.vue';
import PrivateTermsText from './PrivateTermsText.vue';
import { auth } from "@/api/auth.js";
import { user } from "@/api/user.js";

export default {
  name: "SignUp",
  components: {
    DarkButton,
    ServiceTermsText,
    PrivateTermsText
  },
  data() {
    return {
      activePicker: null,
      birth: null,
      menu: false,
      checkbox: false,
      form: {
        email: "",
        password: "",
        name: "",
        nickname: "",
        birthday: "",
        gender: "",
        loginType:"SITE"
      },

      confirmPw: "",

      checkEmailIcon: false,
      checkNicknameIcon: false,

      genders: ["남자", "여자"],

      serviceDialog: false,
      privateDialog: false,

      signUpValue: "회원가입",
    };
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    },
  },
  computed: {
    rules: () => validateRules,
  },
  methods: {
    save(birth) {
      this.$refs.menu.save(birth);
    },
    checkingEmail() {
      if (!this.$refs.email.validate())
        return;

      user.checkEmail(this.form.email,
      ()=> {
        this.checkEmailIcon =true;
      },
      ()=> {
       this.checkEmailIcon =false;
      })

    },
    checkingNickname() {
      if (!this.$refs.nickname.validate())
        return;

      user.checkNickname(this.form.nickname,
      ()=> {
        this.checkNicknameIcon =true;
      },
      ()=> {
       this.checkNicknameIcon =false;
      })
    },
    requestSignUp() {
      const validate = this.$refs.form.validate();

      if(validate && this.checkEmailIcon && this.checkNicknameIcon) {
        auth.signUp(
          this.form,
          (response) => {
            this.$dialog.message.info(`${response.data["content"].nickname}님, 환영합니다!`, {
              position: "top",
              timeout: 2000,
              color: "#0c0f66",
            });
            this.$router.push("/auth/sign-in");
          },
          () => {
          }
        );
      } else {
        this.$dialog.message.info('회원가입 입력 형식을 맞춰주세요.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
      }

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

#signUp {
  width: 30%;
  margin: 0 auto;
}

#check {
  display: table;
}

#checkInput {
  display: table-cell;
  padding-right: 10px;
  width: 100%;
}

input:disabled {
  color: black;
}

#checkBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}

#checkEmailBtn::before {
  background-color: transparent;
}

#checkEmailBtn i:hover {
  transform: scale(1.5);
}

#checkNicknameBtn::before {
  background-color: transparent;
}

#checkNicknameBtn i:hover {
  transform: scale(1.5);
}

#signUpBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}

#terms {
  color: #757575;
  font-size: 12px;
  text-align: left;
}

#termsDialog {
  color: #0c0f66;
  text-decoration: underline;
  display: inline;
}

#dialogTitle {
  justify-content : center;
}

::v-deep ::-webkit-scrollbar {
  width: 10px;
  background: #f0f0f0;
  border-radius: 10px;
}

::v-deep ::-webkit-scrollbar-thumb {
  background: #0c0f66;
  border-radius: 10px;
}

#closeBtn {
  background-color: #0c0f66;
  color: #fff;
}
</style>
