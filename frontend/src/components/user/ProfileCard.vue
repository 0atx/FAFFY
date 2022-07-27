<!--
작성자 : 박윤하
프로필 뷰 - 좌측 프로필 정보
-->
<template>
  <div id="profileCard">
    <!-- 프로필 이미지
      아래 이미지 v-if로 사진 없을 때 디폴트 사진을 보여주던가 해야할거 같음
    -->
    <v-avatar
      color="#fff"
      class="mt-8 mb-4"
      size="250"
      rounded
    >

      <v-img src="@/assets/images/default_profile.png"></v-img>
      <!--
      <v-img src="https://cdn.vuetifyjs.com/images/profiles/marcus.jpg"></v-img>
      -->
    </v-avatar>

    <!-- 유저 프로필 정보 -->
    <div id="profile">
      <!-- 별명 -->
      <div id="name" class="text-h6 mb-2">
        별명짓기귀찮다

        <!-- 정보 수정 버튼
          v-if로 본인일 경우에만 보여지게
          클릭 시 정보 수정 페이지로 이동
        -->
        <v-btn
          v-if="userInfo"
          class="mx-2 pb-1"
          elevation="0"
          icon
          small
          color="gray"
          to=""
        >
          <v-icon>
            mdi-pencil
          </v-icon>
        </v-btn>

        <!-- 팔로우 버튼
          v-if로 본인이 아닐 경우에만 보여지게
          팔로우 중이 아니라면 기본 회색 아이콘(gray),
          팔로우 중이라면 다른 색 아이콘(임시 : #ff7451)
        -->
        <v-btn
          v-else
          class="mx-2 pb-1"
          elevation="0"
          icon
          small
          :color=" follow ? '#ff7451' : 'gray'"
        >
          <v-icon>
            mdi-heart
          </v-icon>
        </v-btn>
      </div>

      <!-- 팔로잉 팔로워 -->
      <div>
        <router-link to="/auth/sign-up">팔로잉 163</router-link>
        <router-link to="/auth/sign-up">팔로워 208</router-link>
      </div>

      <!-- 한 줄 자기소개 -->
      <div id="introduce">
        한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자? 200자? 이거도 의논해야 함
      </div>

      <!-- 관심 카테고리 -->
      <div id="category" class="mb-2">
        <v-chip-group
          column
        >
          <category-chips
            v-for="category in categorys"
            :key="category"
            :category="category"
          />
        </v-chip-group>
      </div>

      <!-- SNS 정보
        v-if로 없으면 보이게 안 보이게 설정하고 있으면 버튼에 링크 달아줘야 함
      -->
      <div id="social" class="mb-2">

        <!-- 인스타그램 -->
        <v-btn
          fab
          elevation="0"
          class="overflow-hidden"
        >
          <img
            src="@/assets/images/instagram_logo.png"
            alt=""
            style="width:60px; height:60px;"
          >
        </v-btn>

        <!-- 페이스북 -->
        <v-btn
          fab
          elevation="0"
          class="overflow-hidden"
        >
          <img
            src="@/assets/images/facebook_logo.png"
            alt=""
            style="width:60px; height:60px;"
          >
        </v-btn>

        <!-- 유튜브 -->
        <v-btn
          fab
          elevation="0"
          class="overflow-hidden"
        >
          <img
            src="@/assets/images/youtube_logo.png"
            alt=""
            style="width:60px; height:60px;"
          >
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryChips from '@/components/common/CategoryChips.vue'

export default {
  name: "ProfileCard",
  components: { CategoryChips },
  data() {
    return {
      // 임의로 설정한 카테고리, 나중에 DB에서 받아온거로 대체 예정
      categorys: ["워크웨어", "히피", "페미닌", "캐주얼", "모던", "시크", "댄디", "빈티지", "미니멀", "스트릿" ],
      readonly: { type: Boolean, default: true },
      userInfo: true,
      follow: true,
    }
  },
  metaInfo () {
    return {
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' }
      ]
    }
  },
};
</script>

<style scoped>
#profileCard {
  background-color: #8b8fda9f;
  padding: 5%;
  position:sticky;
  top: 40px;
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
}

#category {
  height: 140px;
}

#info {
  display: flex;
  justify-content: space-evenly;
}

#name {
  font-weight: 600;
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
</style>
