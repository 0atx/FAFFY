<template>
  <div id="view">
    <v-row id="ProfileView">
      <v-col class="pl-0 pr-0" cols="6" md="4">
        <profile-card />
      </v-col>
      <v-col class="pl-0 pr-0" cols="12" md="8">
        <router-view></router-view>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import ProfileCard from "@/components/user/ProfileCard.vue";
import { mapState, mapActions } from "vuex";

const profileStore = "profileStore";

export default {
  name: "ProfileView",
  components: {
    ProfileCard,
  },
  date() {
    return {
      prevUserNo: 0,
    };
  },
  computed: {
    ...mapState(profileStore, ["userProfile", "followerList", "followingList"]),
  },
  mounted() {
    this.loadProfile();
  },
  watch: {
    $route() {
      if (this.prevUserNo == this.$route.params.no) return;
      this.prevUserNo = this.$route.params.no;
      this.loadProfile();
    },
  },
  methods: {
    ...mapActions(profileStore, [
      "getUserProfile",
      "loadFollower",
      "loadFollowing",
    ]),
    loadProfile() {
      this.loadUserInfo();
      this.loadFollower(this.userProfile.no);
      this.loadFollowing(this.userProfile.no);
    },
    async loadUserInfo() {
      const requestUserNo = this.$route.params.no;

      console.log("유저 정보 요청하기");
      await this.getUserProfile(requestUserNo);
      if (!this.userProfile) {
        console.log("회원 정보가 없음");
        this.$router.push({ name: "main" });
      }

      console.log("프로필 사진 불러오기");
    },
  },
};
</script>

<style scoped>
#view {
  padding-left: 10%;
  padding-right: 10%;
}

#profileView {
  display: flex;
}

#profileCard {
  padding-right: 0;
}
</style>
