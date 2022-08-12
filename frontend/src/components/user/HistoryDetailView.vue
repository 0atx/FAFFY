<!--
작성자 : 박윤하
프로필 뷰 - 방송 기록 상세 정보 페이지
-->
<template>
  <div id="historyDetail">
    <div>
      <!-- 경로 : 임시 라우터 경로, 추후 변경 필요 -->
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link :to="{ name: 'profile' }"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">
          <router-link :to="{ name: 'user-history' }"> History </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">Detail</p>
      </div>

      <div id="content" class="mt-4">
        <div style="display: flex; justify-content: space-evenly">
          <!-- 컨설턴트 정보 -->
          <v-card class="mr-2" style="height: 300px; margin: auto" outlined>
            <v-list-item>
              <v-list-item-content>
                <div style="height: 80px; display: flex; align-items: center">
                  <v-list-item-avatar circle size="60" color="grey"
                    ><v-img src="@/assets/images/default_profile.png"></v-img
                  ></v-list-item-avatar>
                  <div>
                    <!-- 컨설턴트 닉네임 -->
                    <v-list-item-title
                      style="font-weight: 600"
                      class="text-h6 mt-4 mb-2"
                    >
                      별명짓기귀찮다
                    </v-list-item-title>

                    <!-- 컨설턴트 팔로잉 / 팔로워 정보 -->
                    <v-list-item-subtitle>
                      <router-link class="ml-1 mr-4" to="/profile/1/following"
                        >팔로잉 163</router-link
                      >
                      <router-link class="ml-4" to="/profile/1/follower"
                        >팔로워 208</router-link
                      >
                    </v-list-item-subtitle>
                  </div>
                </div>

                <!-- 컨설턴트 한 줄 자기소개 -->
                <v-list-item id="introduce"
                  >소개 들어갈 부분입니다. 근데 간단 소개로 넣을지 상세 소개로
                  넣을지 고민 중 또 넣을거 없나?</v-list-item
                >

                <!-- 컨설턴트 관심 카테고리
                여러 개 설정 가능하지만 여기에선 3개만 보여주게 해야 이쁨 -->
                <v-chip-group column>
                  <category-chips
                    v-for="category in userCategorys"
                    :key="category"
                    :category="category"
                  />
                </v-chip-group>
              </v-list-item-content>
            </v-list-item>
            <v-card-actions>
              <!-- 컨설턴트 방송 알림 설정
              이지만 일단은 누르면 해당 컨설턴트의 상세 프로필 페이지로 이동 -->
              <v-btn
                class="ml-2"
                outlined
                rounded
                text
                to="/profile/1/user-detail"
              >
                알림 설정
              </v-btn>
              <v-spacer></v-spacer>

              <!-- 팔로우 버튼
              1. 컨설턴트가 본인이라면 아무것도 안 보임
              2. 본인이 아니고 팔로우를 하지 않았다면 팔로우 버튼
              3. 본인이 아니고 팔로우를 한 상태라면 팔로잉 버튼(누르면 팔로우 취소) -->
              <v-btn class="mr-2" outlined rounded text> 팔로우 </v-btn>
            </v-card-actions>
          </v-card>

          <!-- 방송 정보 -->
          <v-card class="ml-2" style="height: 300px" outlined>
            <v-list-item>
              <v-list-item-content>
                <div style="display: flex; align-items: center">
                  <div>
                    <!-- 방송 제목 -->
                    <v-list-item-title
                      style="font-weight: 600"
                      class="text-h6 mt-6 mb-2"
                    >
                      방송 제목 나올 자리
                    </v-list-item-title>

                    <!-- 방송 시간 및 시청자 수..넣고 싶었는데 팀장이 몽둥이 들었음 눈물난다 그래서 방송일자랑 시간으로 바꿈ㅜ -->
                    <div style="display: flex; justify-content: space-around">
                      <v-list-item-subtitle> 2022-08-01 </v-list-item-subtitle>
                      <v-list-item-subtitle style="text-align: right">
                        2:54:12 || 17:00:04 ~ 18:49:23
                      </v-list-item-subtitle>
                    </div>
                  </div>
                </div>

                <!-- 방송 소개 -->
                <v-list-item id="introduce"
                  >방송 소개 들어갈 부분입니다. 근데 뭔가 더 넣고 싶은데 넣을게
                  없네...?</v-list-item
                >

                <!-- 방송 카테고리 -->
                <v-chip-group class="mt-2" column>
                  <category-chips
                    v-for="category in consultCategorys"
                    :key="category"
                    :category="category"
                  />
                </v-chip-group>
              </v-list-item-content>
            </v-list-item>
            <v-card-actions>
              <v-spacer></v-spacer>

              <!-- 임시 버튼, 방송 일자 넣어도 되고 다른 정보 넣거나 없앨 수도 있음. 이 버튼은 눌러도 아무 기능 없게 할거임 -->
              <v-btn class="mr-1" outlined rounded text> 2022.08.01 </v-btn>
            </v-card-actions>
          </v-card>
        </div>

        <!-- 스냅샷 목록 -->
        <div class="mt-10">
          <p class="text-h6" style="margin: 0px; font-weight: 600">스냅샷</p>
          <hr />
          <v-row style="margin: 0px">
            <!-- 이미지 리스트 불러와서 숫자 놀이 해야함ㅎㅎ -->
            <v-col v-for="n in 9" :key="n" class="d-flex child-flex" cols="4">
              <v-card>
                <v-img
                  :src="`https://picsum.photos/500/300?image=${n * 5 + 10}`"
                  :lazy-src="`https://picsum.photos/10/6?image=${n * 5 + 10}`"
                  aspect-ratio="1"
                  class="grey lighten-2"
                >
                  <template v-slot:placeholder>
                    <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                    >
                      <v-progress-circular
                        indeterminate
                        color="grey lighten-5"
                      ></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </v-card>
            </v-col>
          </v-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryChips from "@/components/common/CategoryChips.vue";
import { mapActions } from 'vuex';
const profileStore = "profileStore";

export default {
  name: "HistoryDetailView",
  components: { CategoryChips },
  data() {
    return {
      // 임시 방송 기록, DB에서 받아와서 넘겨줘야 함
      consult: [
        {
          title: "방송 제목입니다.",
          intro: "카테고리 넣고 상세 페이지에는 그 날 입었던 옷들 기록?",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
      ],
      // 임의로 설정한 유저 관심 카테고리, 나중에 DB에서 받아온거로 대체 예정
      userCategorys: ["캐주얼", "모던", "시크"],
      // 임의로 설정한 방송 관심 카테고리, 나중에 DB에서 받아온거로 대체 예정
      consultCategorys: ["모던", "미니멀"],
    };
  },
  methods: {
    ...mapActions(profileStore, ['loadHistoryDetail']),
  },
  created() {
    const payload = {
      user_no: this.$route.params.no,
      consulting_no: this.$route.params.consultNo
    }
    this.loadHistoryDetail(payload)
  }
};
</script>

<style scoped>
#historyDetail {
  background-color: #fff;
  padding: 5%;
  padding-bottom: 2%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: flex-start;
  cursor: default;
}

#route {
  display: flex;
  justify-content: start;
}

a {
  color: #666;
  text-decoration: none;
}

#historyDetail div {
  width: 100%;
}

#content {
  background-color: #fff;
  padding: 2%;
}

#introduce {
  height: 80px;
  display: flex;
  padding: 0;
  justify-content: center;
  align-items: center;
  pointer-events: none;
}

button:before {
  background-color: transparent;
}
</style>
