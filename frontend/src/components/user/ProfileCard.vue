<!--
작성자 : 박윤하
프로필 뷰 - 좌측 프로필 정보
-->
<template>
  <div id="profileCard">
    <!-- 유저 프로필 정보 -->
    <div id="profile" class="ml-7">
      <!-- 프로필 이미지 -->
      <v-avatar color="#fff" class=" mb-4" size="250" rounded>
        <img
          :src="`${API_BASE_URL}/users/profile/image/${userProfile.no}`"
          @error="replaceByDefault"
        />
      </v-avatar>

      <!-- 별명 -->
      <div id="name" class="text-h6 ml-8 mb-2">
        {{ userProfile.nickname }}

        <!-- 정보 수정 버튼 -->
        <v-btn
          v-if="isMyProfile"
          id="profileBtn"
          class="mx-2 pb-1"
          elevation="0"
          icon
          small
          color="gray"
          :ripple="false"
          to="/edit-profile"
        >
          <v-icon> mdi-pencil </v-icon>
        </v-btn>

        <!-- 팔로우 버튼
          v-if로 본인이 아닐 경우에만 보여지게
          팔로우 중이 아니라면 기본 회색 아이콘(gray),
          팔로우 중이라면 다른 색 아이콘(임시 : #ff7451)
        -->
        <follow-button v-else :user_no="userProfile.no"/>
      </div>

      <!-- 팔로잉 팔로워 -->
      <div>
        <router-link :to="{ name: 'following' }"
          >팔로잉 {{ userProfile.followingCount }}</router-link
        >
        <router-link :to="{ name: 'follower' }"
          >팔로워 {{ userProfile.followerCount }}</router-link
        >
      </div>

      <!-- 한 줄 자기소개 -->
      <div id="introduce">
        {{ userProfile.info }}
      </div>

      <!-- 관심 카테고리 -->
      <div id="category" class="mb-2">
        <v-chip-group column>
          <category-chips
            v-for="category in userProfile.categories"
            :key="category"
            :category="category"
          />
        </v-chip-group>
      </div>

      <!-- SNS 정보 -->
      <div class="btn_wrap mb-2">
        <span class="snsSpan">SNS</span>
        <div class="btncontainer">
          <!-- 인스타그램 -->
          <v-btn
            icon
            elevation="0"
            :style="userProfile.instaLink ? '' : 'pointer-events: none;'"
            @click="openSNS(userProfile.instaLink)"
          >
            <v-icon :style="userProfile.instaLink ? '' : 'color: #0c0f6620 !important;'" class="snsIcon"> mdi-instagram </v-icon>
          </v-btn>

          <!-- 페이스북 -->
          <v-btn
            icon
            elevation="0"
            :style="userProfile.facebookLink ? '' : 'pointer-events: none;'"
            @click="openSNS(userProfile.facebookLink)"
          >
            <v-icon :style="userProfile.facebookLink ? '' : 'color: #0c0f6620 !important;'" class="snsIcon"> mdi-facebook </v-icon>
          </v-btn>

          <!-- 유튜브 -->
          <v-btn
            icon
            elevation="0"
            :style="userProfile.youtubeLink ? '' : 'pointer-events: none;'"
            @click="openSNS(userProfile.youtubeLink)"
          >
            <v-icon :style="userProfile.youtubeLink ? '' : 'color: #0c0f6620 !important;'" class="snsIcon"> mdi-youtube </v-icon>
          </v-btn>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import CategoryChips from "@/components/common/CategoryChips.vue";
import FollowButton from "@/components/user/FollowButton.vue";
import { mapState } from "vuex";
import { API_BASE_URL } from "@/config";
import defaultProfileSetter from "@/utils/defaultProfileSetter.js";

const profileStore = "profileStore";
const authStore = "authStore";
export default {
  name: "ProfileCard",
  components: { CategoryChips, FollowButton },
  computed: {
    ...mapState(profileStore, ["userProfile"]),
    ...mapState(authStore, ["loginUser","isLogin","followingList"]),
    isMyProfile: function() {
      if (!this.isLogin)
        return false;
      return this.loginUser.no == this.userProfile.no;
    },
    follow: function() {
      if (this.isMyProfile)
        return false;

      let isFollowing = false;
      this.followingList.forEach(element => {
        if (element.no === this.userProfile.no)
          isFollowing = true;
      });
      return isFollowing;
    }
  },
  data() {
    return {
      readonly: { type: Boolean, default: true },
      API_BASE_URL: API_BASE_URL,
    };
  },
  methods: {
    replaceByDefault: defaultProfileSetter.replaceByDefault,
    openSNS(val) {
      window.open(val);
    },

  },
  metaInfo() {
    return {
      meta: [
        { charset: "utf-8" },
        { name: "viewport", content: "width=device-width, initial-scale=1" },
      ],
    };
  },
};
</script>

<style scoped>
#profileCard {
  background-color: #0c0f6620;
  padding: 5%;
  position: sticky;
  top: 0px;
  height:100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

#profile {
  width: 80%;
  overflow: hidden;
  margin: auto;
}

#introduce {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
}

#category {
  height: 70px;
  margin-left: 23%;
}

#categoryChips {
  background-color: #0c0f66;
  color: #fff;
  pointer-events: none;
}

#name {
  font-weight: 600;
  cursor: default;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#profileBtn {
  cursor: pointer;
}

#profileBtn::before {
  background-color: transparent;
}

#profileBtn i:hover {
  transform: scale(1.5);
}

a {
  color: black !important;
  margin: 30px;
  text-decoration: none;
}

#social {
  display: flex;
  justify-content: space-around;
}

img {
  object-fit: cover;
}

.btncontainer i {
    opacity: 0;
    font-size: 28px;
    color: #0c0f66 !important;
    will-change: transform;
    -webkit-transform: scale(.1);
            transform: scale(.1);
    -webkit-transition: all .3s ease;
    transition: all .3s ease;
}

.btncontainer button::before {
  background-color: transparent;
}

.btn_wrap {
    position: relative;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
        -ms-flex-pack: center;
            justify-content: center;
    -webkit-box-align: center;
        -ms-flex-align: center;
            align-items: center;
    overflow: hidden;
    cursor: default;
    width: 240px;
    height: 72px;
    background-color: #0c0f6620;
    border-radius: 80px;
    padding: 0 18px;
    margin:0 auto;
    will-change: transform;
    -webkit-transition: all .2s ease-in-out;
    transition: all .2s ease-in-out;
}

.btn_wrap:hover {
  /* transition-delay: .4s; */
  -webkit-transform: scale(1.1);
          transform: scale(1.1)
}

.snsSpan {
  position: absolute;
  z-index: 99;
  width: 240px;
  height: 72px;
  border-radius: 80px;
  font-size: 20px;
  font-weight: 600;
  text-align: center;
  line-height: 70px;
  letter-spacing: 2px;
  color: #fff;
  background-color: #0c0f66;
  padding: 0 18px;
  -webkit-transition: all 1.2s ease;
  transition: all 1.2s ease;
}

.btncontainer {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-pack: distribute;
      justify-content: space-around;
  -webkit-box-align: center;
      -ms-flex-align: center;
          align-items: center;
  width: 240px;
  height: 64px;
  border-radius: 80px;
}

.btncontainer i:nth-of-type(1) {
  -webkit-transition-delay: .9s;
          transition-delay: .9s;
}

.btncontainer i:nth-of-type(2) {
  -webkit-transition-delay: .7s;
          transition-delay: .7s;
}

.btncontainer i:nth-of-type(3) {
  -webkit-transition-delay: .4s;
          transition-delay: .4s;
}

.btn_wrap:hover span {
  -webkit-transition-delay: .25s;
          transition-delay: .25s;
  -webkit-transform: translateX(-280px);
          transform: translateX(-280px)
}

.btn_wrap:hover i {
    opacity: 1;
    -webkit-transform: scale(1);
            transform: scale(1);
}

.btn_wrap:hover button:hover {
    opacity: 1;
    -webkit-transform: scale(1.25);
            transform: scale(1.25);
}
</style>
