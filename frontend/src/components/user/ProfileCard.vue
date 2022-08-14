<!--
작성자 : 박윤하
프로필 뷰 - 좌측 프로필 정보
-->
<template>
  <div id="profileCard">
    <!-- 유저 프로필 정보 -->
    <div id="profile" class="ml-7">
      <!-- 프로필 이미지 -->
      <v-avatar color="#fff" class="mt-8 mb-4" size="250" rounded>
        <img
          :src="`${API_BASE_URL}/users/profile/image/${userProfile.no}`"
          @error="replaceByDefault"
        />
      </v-avatar>

      <!-- 별명 -->
      <div id="name" class="text-h6 ml-8 mb-2">
        {{ userProfile.nickname }}

        <!-- 정보 수정 버튼
          v-if로 본인일 경우에만 보여지게
          클릭 시 정보 수정 페이지로 이동
        -->
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
        <!-- <v-btn
          v-else
          id="profileBtn"
          class="mx-2 pb-1"
          elevation="0"
          icon
          small
          :color="follow ? '#ff7451' : 'gray'"
        >
          <v-icon> mdi-heart </v-icon>
        </v-btn> -->
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
      <div id="social" class="mb-2">
        <!-- 인스타그램 -->
        <v-btn
          fab
          elevation="0"
          @click="openSNS(userProfile.instaLink)"
          :class="userProfile.instaLink ? 'overflow-hidden' : 'overflow-hidden hiddenBtn'"
        >
          <img
            src="@/assets/images/instagram_logo.png"
            alt=""
            style="width: 60px; height: 60px"
          />
        </v-btn>

        <!-- 페이스북 -->
        <v-btn
          fab
          elevation="0"
          @click="openSNS(userProfile.facebookLink)"
          :class="userProfile.facebookLink ? 'overflow-hidden' : 'overflow-hidden hiddenBtn'"
        >
          <img
            src="@/assets/images/facebook_logo.png"
            alt=""
            style="width: 60px; height: 60px"
          />
        </v-btn>

        <!-- 유튜브 -->
        <v-btn
          fab
          elevation="0"
          @click="openSNS(userProfile.youtubeLink)"
          :class="userProfile.youtubeLink ? 'overflow-hidden' : 'overflow-hidden hiddenBtn'"
        >
          <img
            src="@/assets/images/youtube_logo.png"
            alt=""
            style="width: 60px; height: 60px"
          />
        </v-btn>
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
  components: { CategoryChips,FollowButton   },
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
  background-color: #8b8fda9f;
  padding: 5%;
  position: sticky;
  top: 0px;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

#profile {
  width: 80%;
  margin: auto;
}

#introduce {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
}

#email {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
}

#category {
  height: 140px;
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
  color: black;
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

.hiddenBtn {
  filter: brightness(30%);
  pointer-events: none;
}
</style>
