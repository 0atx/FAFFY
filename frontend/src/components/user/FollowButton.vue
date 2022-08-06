<template>
  <v-btn
    id="profileBtn"
    class="mx-2 pb-1"
    elevation="0"
    icon
    small
    :color="follow ? '#ff7451' : 'gray'"
    @click="toggleFollow"
  >
    <v-icon> mdi-heart </v-icon>
  </v-btn>
</template>

<script>
import { mapState,mapActions } from "vuex";
import {follow} from "@/api/user.js";

const authStore = "authStore";
export default {
  name:"FollowBtn",
  props: {
    user_no:Number,
  },
  computed: {
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
        console.log(element.no);

        if (element.no === this.user_no)
          isFollowing = true;
      });
      return isFollowing;
    }
  },
  methods: {
    ...mapActions(authStore,["loadFollowing"]),
    async toggleFollow() {
      if(this.isMyProfile)
        return;
      if (this.follow) {
        follow.followCancel(
        {
          from:this.loginUser.no,
          to:this.user_no
        },
        (response)=> {
          console.log("성공");
          console.log(response);
          this.loadFollowing();
        },
        (response)=> {
          console.log("실패");
          console.log(response);
        });
      } else {
        follow.follow(
        {
          from:this.loginUser.no,
          to:this.user_no
        },
        (response)=> {
          console.log("성공");
          console.log(response);
          this.loadFollowing();
        },
        (response)=> {
          console.log("실패");
          console.log(response);
        });
      }
      // await this.loadFollowing();
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
  transform: scale(1.5);
}

</style>
