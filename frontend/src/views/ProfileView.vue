<template>
  <div id="view">
    <v-row
      id="ProfileView"
    >
      <v-col
      class="pl-0 pr-0"
        cols="6"
        md="4"
      >
        <profile-card/>
      </v-col>
      <v-col
        class="pl-0 pr-0"
        cols="12"
        md="8"
      >
        <router-view></router-view>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import ProfileCard from '@/components/user/ProfileCard.vue'
import {mapState, mapActions} from "vuex"
const profileStore = "profileStore";

export default {
	name: "ProfileView",
  components: {
    ProfileCard
  },
  computed: {
    ...mapState(profileStore,["userProfile"]),
  },
  data() {
    return {
      profile:null,
    };
  },
  mounted() {
    this.loadProfile();
  },
  methods: {
    ...mapActions(profileStore,["getUserProfile"]),
    async loadProfile() {
      const requestUserNo = this.$route.params.no;
      // 이미 불러온 정보가 있다면 요청하지 않음
      if (this.userProfile != null && this.userProfile.no == requestUserNo)
        return;

      console.log("정보 요청하기");
      await this.getUserProfile(requestUserNo);
      this.profile = this.userProfile;
    }
  }
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
