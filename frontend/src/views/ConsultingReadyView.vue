<template>
  <div id="view">
    <div class="mb-4">
      <video id="video" autoplay style="width: 98%; max-width: 500px; border-radius: 5px;" />
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
            v-model="form.introduce"
            label="방송 소개"
            rows="2"
            no-resize
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
          >
            방송 시작
          </v-btn>
        </div>
      </v-form>

    </div>
  </div>
</template>

<script>
export default {
  name: 'ConsultingFormView',
  data() {
    return {
      // 방송 정보
      form: {
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

  },
  methods: {
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
