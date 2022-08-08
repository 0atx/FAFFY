<!--
작성자 : 박윤하
프로필 뷰 - 팔로워 목록
-->
<template>
  <div id="follower">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link :to="{ name: 'profile' }"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">Following</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600">팔로워 목록</p>
        <hr />
        <div id="followerList">
          <user-list :list="followerList" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { API_BASE_URL } from "@/config";
import UserList from "./UserList.vue";

const profileStore = "profileStore";
export default {
  name: "FollowerView",
  components: {
    UserList,
  },
  computed: {
    ...mapState(profileStore, ["followerList"]),
  },
  mounted() {
    this.IMG_BASE_URL = API_BASE_URL + "/users/profile/image";
  },
  data() {
    return {
      IMG_BASE_URL: null,

      followValue: "팔로우",
    };
  },
};
</script>

<style scoped>
#follower {
  background-color: #fff;
  padding: 5%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  cursor: default;
}

#route {
  display: flex;
  justify-content: flex-start;
}

a {
  color: black;
  text-decoration: none;
}

#follower div {
  width: 100%;
}

#content {
  background-color: #fff;
  padding: 2%;
}

#itemContent {
  display: table;
}

#item {
  display: table-cell;
  vertical-align: middle;
  padding-right: 10px;
  width: 500px !important;
}

#followBtn {
  display: table-cell;
  vertical-align: middle;
  width: 60px !important;
}

button:before {
  background-color: transparent;
}
</style>
