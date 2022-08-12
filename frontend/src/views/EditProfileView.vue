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
          <v-form
            ref="form"
            method="post"
            enctype="multipart/form-data"
            @submit.prevent="requestEdit"
          >
            <!-- 프로필 이미지 src 변수로 받아와서 적용시켜줘야 함 -->
            <div style="text-align: center">
              <v-avatar
                id="avatar"
                color="#fff"
                class="mt-8 mb-4"
                size="250"
                rounded
              >
                <v-img v-if="url" :src="url" @error="replaceByDefault"></v-img>
                <v-img v-else src="@/assets/images/default_profile.png"></v-img>
              </v-avatar>
            </div>

            <v-file-input
              @change="fileChanged"
              v-model="form.file"
              accept="image/*"
              label="프로필 이미지 선택"
              prepend-icon=""
              append-icon="mdi-camera"
              color="#0c0f66"></v-file-input>
            <v-btn id="deleteProfileImgBtn" class="mb-4" block rounded elevation="0" @click="deleteProfileImg">
              프로필 이미지 삭제
            </v-btn>

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
                  @input="checkingNickname"
                  @keydown.enter="onInputKeyword"
                  ref="nickname"
                />
              </div>

              <!-- 별명 중복 확인 -->
              <v-btn
                id="checkNicknameBtn"
                class="mt-2"
                icon
                :ripple="false"
              >
                <v-icon :color="checkNicknameIcon ? '#0c0f66' : '#ff4c20'"
                  >mdi-check</v-icon
                >
              </v-btn>
            </div>

            <!-- 비밀번호 입력 -->
            <v-text-field
              v-model="form.password"
              type="password"
              :rules="
                form.password != undefined && form.password != ''
                  ? rules.password()
                  : [true]
              "
              hint="비밀번호를 변경할 경우에만 입력하세요. 8~16자 영문 대 소문자, 숫자, 특수문자 포함"
              persistent-hint
              label="비밀번호"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            />

            <!-- 비밀번호 확인 -->
            <v-text-field
              v-model="form.confirmPw"
              type="password"
              :rules="
                form.password != undefined && form.password != ''
                  ? [rules.matchValue(form.password)]
                  : [true]
              "
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
              v-model="form.introduce"
              label="상세 자기소개"
              rows="4"
              clearable
              no-resize
              clear-icon="mdi-close-circle"
              color="#0c0f66"
              maxlength="300"
              counter="300"
            ></v-textarea>

            <!-- 관심 카테고리 -->
            <v-label>관심 분야</v-label>
            <v-chip-group v-model="form.categories" column multiple>
              <v-chip
                v-for="(c, i) in categoryList"
                :key="i"
                class="chip"
                :value="c.name"
                filter
                color="#0c0f66"
              >
                {{ c.name }}</v-chip
              >
            </v-chip-group>

            <!-- SNS 링크 instagram -->
            <v-text-field
              v-model="form.instaLink"
              label="인스타그램 링크"
              :rules="rules.instagram()"
              placeholder="https://www.instagram.com/faffy"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            ></v-text-field>

            <!-- SNS 링크 facebook -->
            <v-text-field
              v-model="form.facebookLink"
              label="페이스북 링크"
              :rules="rules.facebook()"
              placeholder="https://www.facebook.com/faffy"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            ></v-text-field>

            <!-- SNS 링크 youtube -->
            <v-text-field
              v-model="form.youtubeLink"
              label="유튜브 링크"
              :rules="rules.youtube()"
              placeholder="https://www.youtube.com/c/faffy"
              color="#0c0f66"
              @keydown.enter="onInputKeyword"
            ></v-text-field>

            <dark-button :btnValue="editValue" @click="requestEdit" />
            <v-btn id="quitBtn" class="mt-4 mb-2" block rounded elevation="0">
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
import { mapState, mapMutations } from "vuex";
import { auth } from "@/api/auth.js";
import { user } from "@/api/user.js";
import { category } from "@/api/category.js";
import { API_BASE_URL } from "@/config";

const authStore = "authStore";
export default {
  name: "EditProfileView",
  components: {
    DarkButton,
  },
  data() {
    return {
      // 임시 user 정보, 추후 DB에서 데이터 불러와서 값 넣어줘야 함
      form: {
        file: null,
        email: "",
        name: "",
        nickname: "",
        password: "",
        confirmPW: "",
        birthday: "",
        gender: "",
        introduce: "",
        info: "",
        categories: [],
        instaLink: "",
        facebookLink: "",
        youtubeLink: "",
      },

      // 유저가 선택한 이미지 url
      url: null,

      // 카테고리 목록
      categoryList: [],

      // 닉네임 중복 확인 후 중복 아닐 때 true 처리
      checkNicknameIcon: true,

      editValue: "정보 수정",
    };
  },
  watch: {
    selectedCategorys: "show",
  },
  mounted() {
    if (!this.isLogin) {
      alert("로그인이 필요합니다.");
      this.$router.push({ name: "main" });
    }
    this.form = { ...this.loginUser };
    if(this.form.gender === 'Female') {
      this.form.gender = '여자'
    } else {
      this.form.gender = '남자'
    }
    console.log(this.form);
    if (this.form.categories == undefined)
      this.form.categories = [];

    this.url = `${API_BASE_URL}/users/profile/image/` + this.loginUser.no;
    // 카테고리 목록 불러오기
    category.getCategories(
      (response) => {
        console.log("카테고리 요청 성공");
        this.categoryList = response.data["content"];
      },
      (response) => {
        console.log("요청 실패");
        console.log(response);
      }
    );
  },
  methods: {
    ...mapMutations(authStore, ["SET_USER_INFO"]),
    replaceByDefault() {
      this.form.url = "@/assets/images/default_profile.png";
      this.fileChanged();
    },
    goTo() {
      this.$router.go(-1);
    },
    checkingNickname() {
      if (!this.$refs.nickname.validate())
        return;

      user.checkNickname(this.form.nickname,
      ()=> {
        this.checkNicknameIcon = true;
      },
      (response)=> {
        console.log("결과:" + response);
        if(this.form.nickname === this.loginUser.nickname) {
          this.checkNicknameIcon = true;
        } else {
          this.checkNicknameIcon = false;
        }
      })
    },
    requestEdit() {
      // 별명, SNS 유효성 검사 확인
      const validate = this.$refs.form.validate();

      // 유효성 검사 안됐으면 alert
      if (!validate) {
        this.$dialog.message.info('입력 형식을 맞춰주세요.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
        return;
      }

      if(this.checkNicknameIcon === false) {
        this.$dialog.message.info('중복된 별명입니다. 다른 별명을 입력하세요.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
        return;
      }
      // 유효성 검사 됐으면 수정 처리

      let formData = new FormData();
      formData.append("no", this.loginUser.no);
      formData.append("nickname", this.form.nickname);
      formData.append("name", this.form.name);
      formData.append("info", this.form.info);
      if (this.form.password != undefined)
        formData.append("password", this.form.password);
      if (this.form.file != null) formData.append("file", this.form.file);
      formData.append("introduce", this.form.introduce);
      formData.append("birthday", this.form.birthday);
      formData.append("instaLink", this.form.instaLink);
      formData.append("facebookLink", this.form.facebookLink);
      formData.append("youtubeLink", this.form.youtubeLink);
      console.log(this.form.categories);
      if (this.form.categories != undefined)
      formData.append("categories", this.form.categories);
      else {
        console.log("unidentifed!");
        formData.append("categories", []);
      }

      auth.updateProfile(
        formData,
        (response) => {
          console.log("성공");
          console.log(response);
          this.SET_USER_INFO(response.data["content"]);
          this.$router.push({
            name: "profile",
            params: { no: this.loginUser.no },
          });
        },
        (response) => {
          console.log("실패");
          console.log(response);
        }
      );
    },
    fileChanged() {
      if (this.form.file != null) {
        this.url = URL.createObjectURL(this.form.file);
      } else {
        this.url = null;
      }
    },
    show() {
      console.log(this.selectedCategorys);
    },
    async deleteProfileImg() {
      const res = await this.$dialog.confirm({
          text: '<br>프로필 이미지를 삭제하시겠습니까?',
          icon: true,
          actions: {
            false : {
              text: '취소', color: '#ff7451'
            },
            true : {
              text: '확인', color: '#0c0f66'
            },
          }
        });
      if (!res) {
        return;
      }

      auth.deleteProfileImg(this.loginUser.no,
      (response) => {
        console.log(response.data);
        this.form.file = null;
        this.url = null;
      },
      (response)=> {
        console.log(response.data);
      })
    }
  },
  computed: {
    rules: () => validateRules,
    ...mapState(authStore, ["isLogin", "loginUser"]),
  },
};
</script>

<style scoped>
.container {
  background-color: white;
  padding: 5%;
  text-align: left;
  display: flex;
  justify-content: flex-start;
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
  justify-content: flex-start;
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

img {
  object-fit: cover;
}

::v-deep ::-webkit-scrollbar {
  width: 0;
  background: transparent;
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
