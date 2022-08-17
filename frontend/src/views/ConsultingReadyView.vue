<template>
  <div id="view">
    <div style="height:375px;" class="mb-4">
      <video id="video" muted autoplay style="max-width: 500px; border-radius: 5px;" />
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
            :rules="rules.Title()"
            @keydown.enter="onInputKeyword"
          />
          <!-- 상세 자기소개 -->
          <v-textarea
            v-model="form.intro"
            label="방송 소개"
            rows="3"
            no-resize
            clearable
            clear-icon="mdi-close-circle"
            color="#0c0f66"
            :rules="rules.consultingIntro()"
            maxlength="300"
            counter="300"
          ></v-textarea>
          <!-- 방송 카테고리 -->
          <div style="display:flex; align-items:center;">
            <v-label>카테고리</v-label><div style="color:#777; font-size:12px; padding:0px 8px; "> *최대 3개 선택 가능</div>
          </div>
          <v-chip-group v-model="form.categories" column multiple max="3">
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
            hint="최대 50명까지 설정할 수 있습니다."
            persistent-hint
            label="최대 인원"
            required
            max=50
            min=2
            color="#0c0f66"
            id = "maxRoom"
            @keyup="checkNum"
            :rules="rules.maxRoom()"
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
import validateRules from "@/utils/validateRules.js";
import {consulting} from "@/api/consulting.js"
import {category} from "@/api/category.js"
import {mapState} from "vuex";

const authStore = "authStore";
export default {
  name: 'ConsultingFormView',
  computed: {
    ...mapState(authStore,["loginUser"]),
    rules: () => validateRules,
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
      categoryList: [],
      // 참여자 수 제한
      maxCounts: [5, 10, 20, 100],
    }
  },
  async created() {
    try{
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      window.stream = stream;
      document.getElementById('video').srcObject = stream;
    }
    catch(error){
      console.log(error)
    }
  },
  mounted() {
    // 내정보, 방송 생성 정보
    this.form.consultant_no = this.loginUser.no;
    // 카테고리 요청
    category.getCategories(
      (response) => {
        this.categoryList = response.data["content"];
      },
      () => {
      }
    );
  },
  methods: {
    // 방송 시작
    start() {
      const validate = this.$refs.form.validate();

      if(validate) {
        consulting.createConsulting(this.form)
        .then(response => {
          console.log("생성 요청 성공");
          console.log(response.content);
          let nickname = response.content.consultant;
          let consulting_no = response.content.no;
          consulting.createConsultingLog({ consulting_no: consulting_no, user_no: this.form.consultant_no })
          this.$router.push({name:"consulting-onair",params:{nickname,consulting_no}});
        })
        .catch(error=> {
          console.log("생성 실패");
          console.log(error);
        })
      } else {
        this.$dialog.message.info('방송 설정 입력 형식을 맞춰주세요.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
      }
    },
    checkNum: function(e) {
      console.log("호출은 되니.." + e.witch + ", " + e.keyCode)
        if(e.keyCode == 187 || e.keyCode == 189) {
        e.target.value = e.target.value.substring(0, e.target.value.length -1);
        return false;
      }
    },
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
  height: 80px !important;
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
