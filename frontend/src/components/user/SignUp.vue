<!--
작성자 : 박윤하
회원가입 페이지
이메일
비밀번호x2
이름
별명
생년월일
성별
이용약관
 -->
 <template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5>
        <h1>회원가입</h1>
      </v-flex>
      <!-- css 임시 -->
      <v-flex xs12 sm6 offset-sm3 mt-3>
        <form>
          <v-layout column>
            <v-flex class="form">
              <v-text-field
                name="email"
                label="이메일"
                id="email"
                type="email"
                required></v-text-field>
                <v-btn class="check" color="primary" type="submit">중복 확인</v-btn>
            </v-flex>
            <v-flex>
              <v-text-field
                name="password"
                label="비밀번호"
                id="password"
                type="password"
                required></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="confirmPassword"
                label="비밀번호"
                id="confirmPassword"
                type="password"
                required></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="name"
                label="이름"
                id="name"
                type="text"
                required></v-text-field>
            </v-flex>
            <v-flex class="form">
              <v-text-field
                name="nickname"
                label="별명"
                id="nickname"
                type="text"
                required></v-text-field>
                <v-btn color="primary" type="submit">중복 확인</v-btn>
            </v-flex>
            <v-flex class="form">
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
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="birth"
                  :active-picker.sync="activePicker"
                  :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
                  min="1950-01-01"
                  @change="save"
                ></v-date-picker>
              </v-menu>
            </v-flex>
            <v-flex>
              <v-text-field
                name="age"
                label="나이"
                id="age"
                type="number"
                required></v-text-field>
            </v-flex>

            <v-flex class="justify-center">
              <v-radio-group
                row
              >
                <template v-slot:label>
                  성별
                </template>
                <v-radio
                  label="남자"
                  value="1"
                ></v-radio>
                <v-radio
                  label="여자"
                  value="2"
                ></v-radio>
                <v-radio
                  label="기타"
                  value="3"
                ></v-radio>
              </v-radio-group>
            </v-flex>

            <v-flex>
              이용약관, 개인정보 취급 방침 등 내용 들어갈 자리
              <v-checkbox v-model="checkbox">
                <template v-slot:label>
                  <div>
                    이용 약관에 동의합니다.
                  </div>
                </template>
              </v-checkbox>
            </v-flex>

            <v-flex class="text-xs-center" mt-5>
              <v-btn color="primary" type="submit">회원가입</v-btn>
            </v-flex>
          </v-layout>
        </form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "SignUp",
  data() {
    return {
      activePicker: null,
      birth: null,
      menu: false,
      checkbox: false,
    }
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = 'YEAR'))
    }
  },
  methods: {
    save(birth) {
      this.$refs.menu.save(birth)
    }
  }
};
</script>

<style>
.form {
  display: flex;
  align-items: center;
}

.justify {
  justify-content: space-around;
}
</style>
