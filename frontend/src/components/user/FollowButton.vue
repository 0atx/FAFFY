<template>
  <v-btn v-if="!isMyProfile"
    id="profileBtn"
    class="mx-2 pb-1"
    elevation="0"
    icon
    small
    :ripple="false"
    :color="follow ? '#ff7451' : 'gray'"
    @click="toggleFollow"
  >
    <v-icon> mdi-heart </v-icon>
  </v-btn>
</template>

<script>
import { mapState,mapActions,mapMutations } from "vuex";
import {follow} from "@/api/user.js";

const profileStore = "profileStore";
const authStore = "authStore";
export default {
  name:"FollowBtn",
  props: {
    user_no:Number,
  },
  computed: {
    ...mapState(profileStore, ["userProfile"]),
    ...mapState(authStore, ["loginUser","isLogin","followingList"]),

    isMyProfile: function() {
      if (!this.isLogin)
        return false;
      return this.loginUser.no == this.user_no;
    },
    follow: function() {
      if (this.isMyProfile)
        return false;

      let isFollowing = false;
      this.followingList.forEach(element => {

        if (element.no === this.user_no)
          isFollowing = true;
      });
      return isFollowing;
    }
  },
  methods: {
    ...mapActions(authStore,["loadFollowing"]),
    ...mapMutations(profileStore, ["SET_USER_PROFILE_FOLLOWERCOUNT"]),
    async toggleFollow() {
      if(!this.isLogin) {
        this.$router.push({ name: 'sign-in' })
      }
      if(this.isMyProfile)
        return;
      if (this.follow) {
        follow.followCancel(
        {
          from:this.loginUser.no,
          to:this.user_no
        },
        ()=> {
          this.loadFollowing();
          // this.SET_USER_PROFILE_FOLLOWERCOUNT(this.userProfile.followerCount-1);
        },
        ()=> {
        });
      } else {
        follow.follow(
        {
          from:this.loginUser.no,
          to:this.user_no
        },
        ()=> {
          this.loadFollowing();
          // this.SET_USER_PROFILE_FOLLOWERCOUNT(this.userProfile.followerCount+1);
        },
        ()=> {
        });
      }
    }
  }
}
</script>

<style scoped>
#profileBtn {
  cursor: pointer;
}

#profileBtn::before {
  background-color: transparent;
}

#profileBtn i:hover {
  transform: scale(1.25);
}

</style>
