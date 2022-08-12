<template>
  <div id="view">
    <div class="mb-4">
      <video id="video" autoplay style="max-width: 500px; border-radius: 5px;" />
    </div>
    <div style="width:70%; margin:0 auto;">
      <v-form ref="form" style="display:flex;justify-content:space-evenly;">
        <div style="width:50%; text-align:left;">
          <!-- 방송 제목 -->
          <v-text-field
            v-model="form.title"
            type="text"
            placeholder="방송 제목을 입력하세요."
            label="방송 제목"
            required
            maxlength="50"
            counter="50"
            color="#0c0f66"
            @keydown.enter="onInputKeyword"
          />
          <!-- 상세 자기소개 -->
          <v-textarea
            v-model="form.intro"
            label="방송 소개"
            rows="2"
            auto-grow
            clearable
            clear-icon="mdi-close-circle"
            color="#0c0f66"
            maxlength="300"
            counter="300"
          ></v-textarea>
          <!-- 방송 카테고리 -->
          <v-label>카테고리</v-label>
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

        </div>
        <div style="width:20%;">
          <!-- 방송 인원 -->
          <v-text-field
            v-model="form.roomSize"
            type="number"
            hint="최대 참여 인원을 입력하세요."
            persistent-hint
            label="최대 인원"
            required
            max=50
            min=0
            color="#0c0f66"
            @keydown.enter="onInputKeyword"
          />
          <v-btn
            id="onairBtn"
            :ripple="false"
            elevation="0"
            @click="start"
          >
            방송 시작
          </v-btn>
        </div>
      </v-form>

    </div>
  </div>
</template>

<script>
import {consulting} from "@/api/consulting.js"
import {category} from "@/api/category.js"
import {mapState} from "vuex";

const authStore = "authStore";
export default {
  name: 'ConsultingFormView',
  computed: {
    ...mapState(authStore,["loginUser"]),
  },
  data() {
    return {
      // 방송 정보
      form: {
        consultant_no:"",
        title: "제목",
        roomSize: 5,
        intro: "정보 소개.",
        categories: [],
      },

      // 카테고리 목록
      categoryList: [
        { name: "캐주얼"},
        { name: "모던"},
        { name: "시크"},
      ],
    }
  },
  async created() {
    try{
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      window.stream = stream;
      document.getElementById('video').srcObject = stream;
    }
    catch(e){
      console.log(e)
    }
  },
  mounted() {
    // 로그인 체크

    // 내정보, 방송 생성 정보
    this.form.consultant_no = this.loginUser.no;
    // 카테고리 요청
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
    // 방송 시작
    start() {
      consulting.createConsulting(this.form)
      .then(response => {
        console.log("생성 요청 성공");
        console.log(response.content);
        let nickname = response.content.consultant;
        let consulting_no = response.content.no;
        this.$router.push({name:"consulting-onair",params:{nickname,consulting_no}});
      })
      .catch(error=> {
        console.log("생성 실패");
        console.log(error);
      })
    }
  },

}
</script>

<style scoped>
#view {
  background-color: white;
  padding: 2%;
  width: 100%;
}

.chip {
  background-color: #0c0f66;
  color: #fff;
}

#onairBtn {
  margin-top: 25%;
  width:200px;
  height: 80px;
  background-color: #ff7451;
  border-radius: 10px;
  color: white;
  font-size:26px;
  font-weight: 600;
}

#onairBtn::before {
  background-color: transparent;
}
</style>
