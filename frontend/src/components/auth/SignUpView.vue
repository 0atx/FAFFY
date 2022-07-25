<!--
작성자 : 박윤하
회원가입 페이지
-->
<template>
  <v-container>
    <img
      src="@/assets/images/faffy_logo_big.png"
      alt="faffy logo"
    >
    <v-form ref="form" id="signUp">
      <div id="check">
        <div id="checkInput">
          <v-text-field
            v-model="account.email"
            type="email"
            :rules="emailRules"
            label="이메일"
            required
            @keydown.enter="onInputKeyword"
          />
        </div>
        <v-btn
          id="checkBtn"
          class="mt-2"
          block
          elevation="0"
          type="submit"
          @click="checkEmail"
        >중복 확인</v-btn>
      </div>

      <v-text-field
        v-model="account.password"
        type="password"
        :rules="[passwordRules.min]"
        label="비밀번호"
        required
        @keydown.enter="onInputKeyword"
      />

      <v-text-field
        v-model="account.password2"
        type="password"
        :rules="[passwordRules.min]"
        label="비밀번호"
        required
        @keydown.enter="onInputKeyword"
      />

      <v-text-field
        name="name"
        label="이름"
        id="name"
        type="text"
        required
      />

      <div id="check">
        <div id="checkInput">
          <v-text-field
            name="nickname"
            label="별명"
            id="nickname"
            type="text"
            required
          />
        </div>

        <v-btn
          id="checkBtn"
          class="mt-2"
          block
          elevation="0"
          type="submit"
          @click="checkNickname"
        >중복 확인</v-btn>
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
          <v-text-field
            v-model="birth"
            label="생년월일"
            append-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="birth"
          color="#0c0f66"
          :active-picker.sync="activePicker"
          :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
          min="1922-01-01"
          @change="save"
        ></v-date-picker>
      </v-menu>

      <v-radio-group
        v-model="account.gender"
        row
        label="성별"
      >
        <v-radio
          label="남자"
          value="1"
        ></v-radio>
        <v-radio
          label="여자"
          value="2"
        ></v-radio>
      </v-radio-group>

      <v-select
        v-model="account.gender"
        :items="genders"
        label="성별"
        required
        @change="$v.select.$touch()"
        @blur="$v.select.$touch()"
      />

      이용약관, 개인정보 취급 방침 등 내용 들어갈 자리
      <v-checkbox v-model="checkbox">
        <template v-slot:label>
          <div>
            이용 약관에 동의합니다.
          </div>
        </template>
      </v-checkbox>

      <v-btn
        id="signUpBtn"
        class="mt-2"
        block
        elevation="0"
        type="submit"
        @click="requestSignUp"
      >회원가입</v-btn>

    </v-form>
  </v-container>
</template>

<script>
export default {
  name: "SignIn",
  data() {
      return {
        activePicker: null,
        birth: null,
        menu: false,
        checkbox: false,
        account: {
            email: '',
            password: '',
            gender: '',
        },

        genders: [
          '남자',
          '여자',
        ],
        valid: false,

        row: null,

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
  watch: {
    menu (val) {
      val && setTimeout(() => (this.activePicker = 'YEAR'))
    },
  },
  methods: {
    save (birth) {
      this.$refs.menu.save(birth)
    },
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

#signUpBtn {
  background-color: #0c0f66;
  color: #fff;
  border: 1px solid #fff;
}
</style>
