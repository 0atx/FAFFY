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
                      {{ historyDetail.userInfoDto.nickname }}
                    </v-list-item-title>

                    <!-- 컨설턴트 팔로잉 / 팔로워 정보 -->
                    <v-list-item-subtitle>
                      <router-link class="ml-1 mr-4" :to="`/profile/${userNo}/following`"
                        >팔로잉 {{ historyDetail.userInfoDto.followingCount }}</router-link
                      >
                      <router-link class="ml-4" :to="`/profile/${userNo}/follower`"
                        >팔로워 {{ historyDetail.userInfoDto.followerCount }}</router-link
                      >
                    </v-list-item-subtitle>
                  </div>
                </div>

                <!-- 컨설턴트 한 줄 자기소개 -->
                <v-list-item id="introduce">
                  {{ historyDetail.userInfoDto.introduce }}
                </v-list-item>

                <!-- 컨설턴트 관심 카테고리
                여러 개 설정 가능하지만 여기에선 3개만 보여주게 해야 이쁨 -->
                <v-chip-group column>
                  <category-chips
                    v-for="category in userCategories"
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
                :to="`/profile/${historyDetail.userInfoDto.no}/user-detail`"
              >
                알림 설정
              </v-btn>
              <v-spacer></v-spacer>

              <!-- 팔로우 버튼
              1. 컨설턴트가 본인이라면 아무것도 안 보임
              2. 본인이 아니고 팔로우를 하지 않았다면 팔로우 버튼
              3. 본인이 아니고 팔로우를 한 상태라면 팔로잉 버튼(누르면 팔로우 취소) -->
              <!-- <v-btn class="mr-2" outlined rounded text v-if="checkUserInfo.no !== historyDetail.userInfoDto.no"> 팔로우 </v-btn> -->
              <follow-button v-if="checkUserInfo.no !== historyDetail.userInfoDto.no" :user_no="historyDetail.userInfoDto.no"></follow-button>
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
                      {{ historyDetail.consultingDto.title }}
                    </v-list-item-title>

                    <!-- 방송 시간 및 시청자 수..넣고 싶었는데 팀장이 몽둥이 들었음 눈물난다 그래서 방송일자랑 시간으로 바꿈ㅜ -->
                    <div style="display: flex; justify-content: space-around">
                      <v-list-item-subtitle> {{ historyDetail.consultingDto.date }} </v-list-item-subtitle>
                      <v-list-item-subtitle style="text-align: right">
                        {{ historyDetail.consultingDto.duration }} || {{ historyDetail.consultingDto.startTime.slice(0, 8) }} ~ {{ historyDetail.consultingDto.endTime.slice(0, 8) }}
                      </v-list-item-subtitle>
                    </div>
                  </div>
                </div>

                <!-- 방송 소개 -->
                <v-list-item id="introduce"
                  >{{ historyDetail.consultingDto.introduce }}</v-list-item
                >

                <!-- 방송 카테고리 -->
                <v-chip-group class="mt-2" column>
                  <category-chips
                    v-for="category in historyDetail.consultingDto.categories"
                    :key="category"
                    :category="category"
                  />
                </v-chip-group>
              </v-list-item-content>
            </v-list-item>
            <v-card-actions>
              <v-spacer></v-spacer>

              <!-- 임시 버튼, 방송 일자 넣어도 되고 다른 정보 넣거나 없앨 수도 있음. 이 버튼은 눌러도 아무 기능 없게 할거임 -->
              <v-btn class="mr-1" outlined rounded text> {{ historyDetail.consultingDto.date }} </v-btn>
            </v-card-actions>
          </v-card>
        </div>

        <!-- 스냅샷 목록 -->
        <div class="mt-10">
          <p class="text-h6" style="margin: 0px; font-weight: 600">스냅샷</p>
          <hr />
          <v-row style="margin: 0px" v-if="isSnapshotExist">
            <!-- 이미지 리스트 불러와서 숫자 놀이 해야함ㅎㅎ -->
            <v-col v-for="snapshot in snapshotList" :key="snapshot" class="d-flex child-flex" cols="4" >
              <v-card>
                <v-img
                  :src="snapshot"
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
          <v-row style="margin: 0px" v-else>
            스냅샷이 존재하지 않습니다.
          </v-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryChips from "@/components/common/CategoryChips.vue";
import { mapActions, mapGetters } from 'vuex';
import FollowButton from '@/components/user/FollowButton.vue'
import _ from 'lodash';
const profileStore = "profileStore";
const authStore = "authStore";

export default {
  name: "HistoryDetailView",
  components: { CategoryChips, FollowButton },
  data() {
    return {
      userNo: this.$route.params.no,
    };
  },
  methods: {
    ...mapActions(profileStore, ['loadHistoryDetail']),
  },
  computed: {
    ...mapGetters(profileStore, ['historyDetail', 'snapshotList']),
    ...mapGetters(authStore, ['checkUserInfo']),
    userCategories() {
      return this.historyDetail.userInfoDto.categories.slice(0, 3)
    },
    isSnapshotExist() {
      return !_.isEmpty(this.snapshotList)
    }
  },
  async created() {
    const payload = {
      user_no: Number(this.$route.params.no),
      consulting_no: Number(this.$route.params.consultNo),
    }
    await this.loadHistoryDetail(payload)
    console.log(this.historyDetail)
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
