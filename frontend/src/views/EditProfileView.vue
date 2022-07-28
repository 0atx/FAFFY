<template>
  <div id="view">
    <v-container>
      <div id="profileDetail">
        <div id="route">
          <p class="text-h6" style="font-weight: 600">
            <a @click="goTo"> Profile </a>
          </p>
          <v-icon color="black" class="mt-1"> mdi-chevron-right </v-icon>
          <p class="text-h6" style="font-weight: 600">Edit</p>
        </div>
        <div id="editInfo">
          <v-form ref="form" method="post" enctype="multipart/form-data" @submit.prevent="requestEdit">

            <!-- 프로필 이미지 src 변수로 받아와서 적용시켜줘야 함 -->
            <div style="text-align: center">
            <v-avatar id="avatar" color="#fff" class="mt-8 mb-4" size="250" rounded>
              <v-img src="@/assets/images/default_profile.png"></v-img>
            </v-avatar>
            </div>

            <v-file-input
              accept="image/*"
              label="프로필 이미지 선택"
              prepend-icon=""
              append-icon="mdi-camera"
            ></v-file-input>

            <!-- 이메일 -->
            <v-text-field
              type="email"
              v-model="user.email"
              label="이메일"
              readonly
            />

            <!-- 이름 -->
            <v-text-field
              type="text"
              v-model="user.name"
              label="이름"
              readonly
            />

            <div id="check">
              <div id="checkInput">
                <!-- 별명 입력 -->
                <v-text-field
                  v-model="user.nickname"
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
              <v-btn id="checkNicknameBtn" class="mt-2" icon @click="checkNickname">
                <v-icon :color="checkNicknameIcon ? '#0c0f66' : '#ff4c20'">mdi-check</v-icon>
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
              @keydown.enter="onInputKeyword"
            />

            <!-- 생년월일 -->
            <v-text-field
              v-model="user.birthday"
              label="생년월일"
              readonly
            ></v-text-field>

            <!-- 성별 -->
            <v-text-field
              v-model="user.gender"
              label="성별"
              readonly
            ></v-text-field>

            <!-- 한 줄 자기소개 -->
            <v-text-field
              v-model="user.gender"
              label="성별"
              readonly
            ></v-text-field>

            <!-- 긴 글 자기소개 -->
            <v-text-field
              v-model="user.gender"
              label="성별"
              readonly
            ></v-text-field>

            <!-- 관심 카테고리 -->
            <v-label>관심 분야</v-label>
            <v-chip-group
              v-model="user.categorys"
              column
              multiple
            >
              <v-chip
                v-for="category in categorys"
                :key="category"
                filter
              > {{ category }}</v-chip>
            </v-chip-group>

            <!-- SNS 링크 instagram -->
            <v-text-field
              v-model="user.gender"
              label="성별"
              readonly
              disabled
            ></v-text-field>

            <!-- SNS 링크 facebook -->
            <v-text-field
              v-model="user.gender"
              label="성별"
              readonly
              disabled
            ></v-text-field>

            <!-- SNS 링크 youtube -->
            <v-text-field
              v-model="user.gender"
              label="성별"
              readonly
              disabled
            ></v-text-field>

            <dark-button :btnValue="editValue" @click="requestEdit" />
            <v-btn
                id="quitBtn"
                class="mt-4 mb-2"
                block
                rounded
                elevation="0"
              >회원 탈퇴</v-btn>
          </v-form>
        </div>

      </div>
    </v-container>
  </div>
</template>

<script>
import DarkButton from "@/components/common/DarkButton.vue";
import validateRules from "@/utils/validateRules.js";

export default {
	name: "EditProfileView",
  components: {
    DarkButton,
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
      },

      user: {
        email: "ha110011@naver.com",
        password: "password",
        name: "박윤하",
        nickname: "별명짓기귀찮다",
        birthday: "1998-11-07",
        gender: "여자",
        categorys: [],
      },

      // 임의로 설정한 카테고리, 나중에 DB에서 받아온거로 대체 예정
      categorys: [
        "워크웨어",
        "히피",
        "페미닌",
        "캐주얼",
        "모던",
        "시크",
        "댄디",
        "빈티지",
        "미니멀",
        "스트릿",
      ],

      confirmPw: "",

      checkNicknameIcon: false,

      genders: ["남자", "여자"],

      editValue: "정보 수정",
    };
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    },
  },
  methods: {
    goTo() {
      this.$router.go(-1);
    },
    checkNickname() {
      console.log("별명 중복 확인 함수 입니다. 버튼색도 바뀌면 좋겠다22..");
    },
    requestEdit() {
      console.log("정보 수정 보내야 합니다.");
    }
  },
  computed: {
    rules: () => validateRules,
  },
};
</script>

<style scoped>
.container {
  background-color: #fff;
  padding: 5%;
  text-align: left;
  display: flex;
  justify-content: start;
  align-items: flex-start;
}

#profileDetail {
  background-color: rgb(110, 127, 212);
  text-align: left;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

#route {
  display: flex;
  justify-content: start;
  align-items: flex-start;
}

a {
  color: black;
  text-decoration: none;
}

#editInfo {
  width: 100%;
  background-color: rgb(212, 110, 120);
  display: flex;
  justify-content: center;
  align-items: center;
}

form {
  width: 40%;
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

#checkNicknameBtn::before {
  background-color: transparent;
}

#checkNicknameBtn i:hover {
  transform: scale(1.15);
}

#quitBtn {
  background-color: #ff7451;
  color: #fff;
}
</style>
