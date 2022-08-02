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
              <v-img v-if="url" :src="url"></v-img>
              <v-img v-else src="@/assets/images/default_profile.png"></v-img>
            </v-avatar>
            </div>

            <v-file-input
              @change="fileChanged"
              v-model="form.img"
              accept="image/*"
              label="프로필 이미지 선택"
              prepend-icon=""
              append-icon="mdi-camera"
              color="#0c0f66"
            ></v-file-input>

            <!-- 이메일 -->
            <v-text-field
              type="email"
              v-model="form.email"
              label="이메일"
              disabled
            />

            <!-- 이름 -->
            <v-text-field
              type="text"
              v-model="form.name"
              label="이름"
              disabled
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
              hint="비밀번호를 변경할 경우에만 입력하세요. 8~16자 영문 대 소문자, 숫자, 특수문자 포함"
              persistent-hint
              label="비밀번호"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            />

            <!-- 비밀번호 확인 -->
            <v-text-field
              v-model="confirmPw"
              type="password"
              :rules="[rules.matchValue(form.password)]"
              hint="변경할 비밀번호를 재입력해주세요."
              persistent-hint
              label="비밀번호 확인"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            />

            <!-- 생년월일 -->
            <v-text-field
              v-model="form.birthday"
              label="생년월일"
              disabled
            ></v-text-field>

            <!-- 성별 -->
            <v-text-field
              v-model="form.gender"
              label="성별"
              disabled
            ></v-text-field>

            <!-- 한 줄 자기소개 -->
            <v-textarea
              v-model="form.info"
              label="한 줄 자기소개"
              color="#0c0f66"
              rows="2"
              no-resize
              maxlength="50"
              counter="50"
            ></v-textarea>

            <!-- 상세 자기소개 -->
            <v-textarea
              v-model="form.infoDetail"
              label="상세 자기소개"
              auto-grow
              clearable
              clear-icon="mdi-close-circle"
              color="#0c0f66"
              maxlength="300"
              counter="300"
            ></v-textarea>

            <!-- 관심 카테고리 -->
            <v-label>관심 분야</v-label>
            <v-chip-group
              v-model="selectedCategorys"
              column
              multiple
            >
              <v-chip
                v-for="(category, i) in categorys"
                :key="i"
                class="chip"
                :value="category"
                filter
                color="#0c0f66"
              > {{ category }}</v-chip>
            </v-chip-group>

            <!-- SNS 링크 instagram -->
            <v-text-field
              v-model="form.instagram"
              label="인스타그램 링크"
              :rules="rules.instagram()"
              placeholder="https://www.instagram.com/faffy"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            ></v-text-field>

            <!-- SNS 링크 facebook -->
            <v-text-field
              v-model="form.facebook"
              label="페이스북 링크"
              :rules="rules.facebook()"
              placeholder="https://www.facebook.com/faffy"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            ></v-text-field>

            <!-- SNS 링크 youtube -->
            <v-text-field
              v-model="form.youtube"
              label="유튜브 링크"
              :rules="rules.youtube()"
              placeholder="https://www.youtube.com/c/faffy"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            ></v-text-field>

            <dark-button :btnValue="editValue" @click="requestEdit" />
            <v-btn
              id="quitBtn"
              class="mt-4 mb-2"
              block
              rounded
              elevation="0"
            >
              회원 탈퇴
            </v-btn>
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
      // 임시 user 정보, 추후 DB에서 데이터 불러와서 값 넣어줘야 함
      form: {
        img: null,
        email: "ha110011@naver.com",
        name: "박윤하",
        nickname: "별명짓기귀찮다",
        password: "Password1!",
        birthday: "1998-11-07",
        gender: "여자",
        info: "간단한 자기소개를 작성해주세요. 작성된 내용은 프로필 카드에 노출됩니다.",
        infoDetail: "자세한 자기소개를 작성해주세요. 작성된 내용은 프로필 상세 페이지에서 확인할 수 있습니다.",
        instagram: "",
        facebook: "",
        youtube: ""
      },

      // 유저가 선택한 이미지 url
      url: null,

      // user가 선택한 카테고리 > DB에 넘겨야 함
      selectedCategorys: "",

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

      confirmPw: "Password1!",

      // 닉네임 중복 확인 후 중복 아닐 때 true 처리
      checkNicknameIcon: false,

      editValue: "정보 수정",
    };
  },
  watch: {
    selectedCategorys: "show",
  },
  methods: {
    goTo() {
      this.$router.go(-1);
    },
    checkNickname() {
      console.log("별명 중복 확인 함수 입니다. 버튼색도 바뀌면 좋겠다22..");
    },
    requestEdit() {
      // 별명, SNS 유효성 검사 확인
      const validate = this.$refs.form.validate();

      // 유효성 검사 안됐으면 alert
      if(!validate) {
        alert('회원가입 입력 형식을 맞춰주세요.')
        return;
      }
      // 유효성 검사 됐으면 수정 처리

      // let formData = new FormData();
      // axios.put('http://localhost:8888/api/users', formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(
      //   response => {
      //     if (!!response && response.status === 200) {
      //       console.log("OK");
      //     }
      //   }).catch(error => {
      //     console.log(error);
      //   });

      console.log("정보 수정 보내야 합니다.");
    },
    fileChanged() {
      if(this.form.img != null) {
        this.url = URL.createObjectURL(this.form.img);
      } else {
        this.url = null;
      }
    },
    show() {
      console.log(this.selectedCategorys);
    }
  },
  computed: {
    rules: () => validateRules,
  },
};
</script>

<style scoped>
.container {
  background-color: white;
  padding: 5%;
  text-align: left;
  display: flex;
  justify-content: start;
  align-items: flex-start;
}

#profileDetail {
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

.chip {
  background-color: #0c0f66;
  color: #fff;
}

#quitBtn {
  background-color: #ff7451;
  color: #fff;
}
</style>
