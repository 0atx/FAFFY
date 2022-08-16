<template>
  <div id="view">
    <v-row id="ProfileView">
      <v-col class="pl-0 pr-0 pb-0" cols="6" md="4">
        <profile-card />
      </v-col>
      <v-col class="pl-0 pr-0 pb-0" cols="12" md="8">
        <router-view></router-view>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import ProfileCard from "@/components/user/ProfileCard.vue";
import { user } from "@/api/user.js";
import { mapState, mapMutations, mapActions } from "vuex";

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
  methods: {
    ...mapActions(profileStore, ["loadFollower", "loadFollowing", "loadParticipatedList", "loadConsultingList", "loadBoardList"]),
    ...mapMutations(profileStore, ["SET_USER_PROFILE"]),
    loadProfile() {
      this.loadUserInfo();
      this.loadFollower(this.$route.params.no);
      this.loadFollowing(this.$route.params.no);
      this.loadParticipatedList(this.$route.params.no);
      this.loadConsultingList(this.$route.params.no);
      this.loadBoardList(this.$route.params.no);
    },
    async loadUserInfo() {
      const requestUserNo = this.$route.params.no;

      console.log(requestUserNo+"번 유저 정보 요청하기");
      user.getUserProfile(
        requestUserNo,
        (response) => {
          console.log(response.data["content"]);
          this.SET_USER_PROFILE(response.data["content"]);
        },
        (response) => {
          console.log(response);
          this.SET_USER_PROFILE(null);
          console.log("회원 정보가 없음");
          this.$router.push({ name: "main" });
        }
      );
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
