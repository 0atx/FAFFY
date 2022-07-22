<!--
작성자 : 박윤하
프로필 뷰 - 좌측 프로필 정보
-->
<template>
    <v-row id="profileCard" no-gutters>
      <v-col>
        <v-avatar
          class="profile"
          size="164"
          tile
        >
          <v-img src="../../assets/images/faffy_logo.png"></v-img>
        </v-avatar>
      </v-col>
      <v-col>
        <v-list-item-content>
          <v-list-item-title class="text-h6">
            이름
          </v-list-item-title>
          <v-list-item-subtitle>
            팔로잉 팔로워
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-col>
    </v-row>
</template>

<script>
export default {
  name: "ProfileCard",
  data() {
      return {

      }
  }
};
</script>

<style scoped>
#profileCard {
  background-color: blueviolet;
  float: left;
}
</style>
