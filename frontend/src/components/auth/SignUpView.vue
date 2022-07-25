<!--
작성자 : 박윤하
회원가입 페이지
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
      id="signUp"
      @submit.prevent="requestSignUp"
    >
      <div id="check">
        <div id="checkInput">
          <!-- 이메일 입력 -->
          <v-text-field
            v-model="form.email"
            type="email"
            :rules="rules.email()"
            label="이메일"
            required
            @keydown.enter="onInputKeyword"
          />
        </div>

        <!-- 이메일 중복 확인 -->
        <v-btn
          icon
          @click="checkEmail"
        >
          <v-icon id="checkEmailBtn">mdi-check</v-icon>
        </v-btn>
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
        @keydown.enter="onInputKeyword"
      />

      <!-- 비밀번호 확인 -->
      <v-text-field
        v-model="confirmPw"
        type="password"
        :rules="[rules.matchValue(form.password)]"
        label="비밀번호 확인"
        required
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
            @keydown.enter="onInputKeyword"
          />
        </div>

        <!-- 별명 중복 확인 -->
        <v-btn
          icon
          @click="checkNickname"
        >
          <v-icon id="checkNicknameBtn">mdi-check</v-icon>
        </v-btn>
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
            v-model="form.birth"
            :rules="rules.birth()"
            label="생년월일"
            append-icon="mdi-calendar"
            readonly
            hide-details
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="form.birth"
          color="#0c0f66"
          :active-picker.sync="activePicker"
          locale="ko=KR"
          :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
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
        <v-radio
          label="남자"
          value="1"
          color="#0c0f66"
        ></v-radio>
        <v-radio
          label="여자"
          value="2"
          color="#0c0f66"
        ></v-radio>
      </v-radio-group>

      <!-- 이용약관 -->
      <div
        id="terms"
        class="mb-4"
      >
        회원가입 시 FAFFY(패피)가 제공하는 서비스를 모두 이용하실 수 있습니다.
        <router-link to="/auth/service-terms">서비스 이용 약관</router-link> 및 <router-link to="/auth/service-terms">개인정보 취급 방침</router-link>에 동의합니다.
      </div>

      <dark-button
        :btnValue="signUpValue"
        @click="requestSignUp"
      />
    </v-form>
  </v-container>
</template>

<script>
import DarkButton from '@/components/common/DarkButton.vue'
import validateRules from '@/utils/validateRules.js'

export default {
  name: "SignUp",
  components: {
    DarkButton
  },
  data() {
      return {
        activePicker: null,
        birth: null,
        menu: false,
        checkbox: false,
        form: {
            email: '',
            password: '',
            name: '',
            nickname: '',
            birth: '',
            gender: '',
        },

        confirmPw: '',

        genders: [
          '남자',
          '여자',
        ],

        signUpValue: '회원가입',
      }
  },
  watch: {
    menu (val) {
      val && setTimeout(() => (this.activePicker = 'YEAR'))
    },
  },
    computed: {
    rules: () => validateRules,
  },
  methods: {
    save (birth) {
      this.$refs.menu.save(birth)
    },
    checkEmail() {
      console.log("이메일 중복 확인 함수 입니다. 버튼색도 바뀌면 좋겠다ㅎㅎ..");
    },
    checkNickname() {
      console.log("별명 중복 확인 함수 입니다. 버튼색도 바뀌면 좋겠다22..");
    },
    requestSignUp() {
      console.log("회원가입 기능 구현해야 합니다.");
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

#checkBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}

#checkEmailBtn {
  color: #ff7451;
}

#checkNicknameBtn {
  color: #0c0f66;
}

#signUpBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}

#terms {
  color: #757575;
  font-size:12px;
  text-align: left;
}

a {
  color: #0c0f66;
}
</style>