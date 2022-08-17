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
          <router-link :to="{ name: 'profile' }"> 정보 </router-link>
        </p>
        <v-icon color="black" class="mb-4"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">
          <router-link :to="{ name: 'user-history' }"> 방송 기록 </router-link>
        </p>
        <v-icon color="black" class="mb-4"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600"> 상세 </p>
      </div>

      <div id="content" class="mt-4">
        <v-row style="display: flex; justify-content: space-evenly">

          <!-- 컨설턴트 정보 -->
          <v-col class="pl-0 pr-1" cols="5">
          <v-card style="min-height:300px; margin: auto" outlined>
            <v-list-item>
              <v-list-item-content class="pb-0">
                <v-list-item-title
                  style="font-weight: 600"
                  class="text-h6"
                >
                  진행자 정보
                </v-list-item-title>
                <div style="height: 80px; display: flex; align-items: center">
                  <v-list-item-avatar circle size="60" color="grey"
                    ><v-img src="@/assets/images/default_profile.png"></v-img
                  ></v-list-item-avatar>
                  <div>
                    <!-- 컨설턴트 닉네임 -->
                    <v-list-item-title
                      style="font-weight: 600"
                      class="text-h6 mb-2"
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
                  {{ consultantIntro }}
                </v-list-item>

                <!-- 컨설턴트 관심 카테고리
                여러 개 설정 가능하지만 여기에선 3개만 보여주게 해야 이쁨 -->
                <v-list-item style="min-height:48px;" class="pl-0 pr-0">
                  <v-chip-group column>
                    <category-chips
                      v-for="category in userCategories"
                      :key="category"
                      :category="category"
                    />
                  </v-chip-group>
                </v-list-item>
              </v-list-item-content>
            </v-list-item>
            <v-card-actions class="pt-1 mb-1">
              <!-- 컨설턴트 방송 알림 설정
              이지만 일단은 누르면 해당 컨설턴트의 상세 프로필 페이지로 이동 -->

              <v-btn
                class="ml-2"
                outlined
                rounded
                text
                :to="`/profile/${historyDetail.userInfoDto.no}/user-detail`"
              >
                상세 프로필
              </v-btn>

              <v-spacer></v-spacer>

              <!-- 팔로우 버튼
              1. 컨설턴트가 본인이라면 아무것도 안 보임
              2. 본인이 아니고 팔로우를 하지 않았다면 팔로우 버튼
              3. 본인이 아니고 팔로우를 한 상태라면 팔로잉 버튼(누르면 팔로우 취소) -->
              <!-- <v-btn class="mr-2" outlined rounded text v-if="checkUserInfo.no !== historyDetail.userInfoDto.no"> 팔로우 </v-btn> -->
              <follow-button class="mr-3" v-if="checkUserInfo.no !== historyDetail.userInfoDto.no" :user_no="historyDetail.userInfoDto.no"></follow-button>
            </v-card-actions>
          </v-card>
          </v-col>

          <!-- 방송 정보 -->
          <v-col class="pr-0" cols="7">
          <v-card style="min-height: 300px" outlined>
            <v-list-item>
              <v-list-item-content class="pb-0">
                <v-list-item-title
                  style="font-weight: 600"
                  class="text-h6"
                >
                  방송 정보
                </v-list-item-title>
                <div style="display: flex; align-items: center">
                  <div style="height:80px;">
                      <!-- 방송 제목 -->
                      <v-list-item-title
                        style="font-weight: 600"
                        class="text-h6 mt-4 mb-2"
                      >
                        {{ historyDetail.consultingDto.title }}
                      </v-list-item-title>

                    <!-- 방송 시간 및 시청자 수..넣고 싶었는데 팀장이 몽둥이 들었음 눈물난다 그래서 방송일자랑 시간으로 바꿈ㅜ -->
                    <div style="display: flex; justify-content: space-around">
                      <v-list-item-subtitle> {{  historyDetail.consultingDto.date.replaceAll('-','.') }} </v-list-item-subtitle>
                      <v-list-item-subtitle style="text-align: right">
                        {{ historyDetail.consultingDto.startTime.slice(0, 8) }} ~ {{ historyDetail.consultingDto.endTime.slice(0, 8) }}
                      </v-list-item-subtitle>
                    </div>
                  </div>
                </div>

                <!-- 방송 소개 -->
                <v-list-item id="introduce">
                  {{ consultingIntro }}
                </v-list-item>

                <!-- 방송 카테고리 -->
                <v-list-item style="min-height:48px;" class="pl-0 pr-0">
                  <v-chip-group class="mt-2" column>
                    <category-chips
                      v-for="category in historyDetail.consultingDto.categories"
                      :key="category"
                      :category="category"
                    />
                  </v-chip-group>
                </v-list-item>
              </v-list-item-content>
            </v-list-item>
            <v-card-actions class="pt-1 mb-1">
              <v-spacer></v-spacer>

              <!-- 임시 버튼, 방송 일자 넣어도 되고 다른 정보 넣거나 없앨 수도 있음. 이 버튼은 눌러도 아무 기능 없게 할거임 -->
              <v-btn id="timeBtn" :ripple="false" class="mr-1" outlined rounded text> {{ consultingTime }} </v-btn>
            </v-card-actions>
          </v-card>
          </v-col>
        </v-row>

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
            <v-col style="height:300px; display:flex; justify-content:center; align-items:center;" cols="12">
              <div style="text-align:center;">
                <v-icon color="#333" large block> mdi-image-off-outline </v-icon>
                <h4>스냅샷 기록이 없습니다.</h4>
              </div>
            </v-col>
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
      consultantIntro: '',
      consultingIntro: '',
      consultingTime: '',
    };
  },
  methods: {
    ...mapActions(profileStore, ['loadHistoryDetail']),
    zeroPadding(num, digit) {
      var zero = '';
      for (var i = 0; i < digit; i++){
        zero += 0;
      }
      return (zero + num).slice(-digit);
    },
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
    if(this.historyDetail.userInfoDto.introduce.length > 100) {
      this.consultantIntro = this.historyDetail.userInfoDto.introduce.slice(0,100) + "...";
    } else {
      this.consultantIntro = this.historyDetail.userInfoDto.introduce;
    }
    if(this.historyDetail.consultingDto.introduce.length > 100) {
      this.consultingIntro = this.historyDetail.consultingDto.introduce.slice(0, 100) + "...";
    } else {
      this.consultingIntro = this.historyDetail.consultingDto.introduce;
    }
    var temp = this.historyDetail.consultingDto.duration.split(':');
    this.consultingTime = this.zeroPadding(temp[0], 2) + ':' + this.zeroPadding(temp[1], 2) + ":" + this.zeroPadding(temp[2], 2);
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
  min-height: 80px;
  display: flex;
  padding: 0;
  text-align: left;
  /* align-items: center; */
  pointer-events: none;
}

a {
  color: black;
  text-decoration: none;
}

button:before {
  background-color: transparent;
}

#timeBtn {
  cursor: default;
}

</style>
