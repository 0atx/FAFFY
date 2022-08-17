<template v-slot:default>
  <v-list style="padding: 0">
    <v-list-item :ripple="false" :key="user.nickname">
      <template v-slot:default>
        <profile-img-avatar :user_no="user.no" />

        <v-list-item-content id="itemContent">
          <div id="item" v-on:click="toDetail()">
            <v-list-item-title
              class="mb-1"
              v-html="user.nickname"
            ></v-list-item-title>
            <v-list-item-subtitle
              class="mt-1"
              v-html="user.info"
            ></v-list-item-subtitle>
          </div>
          <follow-button class="mt-2" :user_no="user.no"/>
        </v-list-item-content>
      </template>
    </v-list-item>
    <v-divider
      :key="user.no"
      inset
    ></v-divider>
  </v-list>
</template>

<script>
import ProfileImgAvatar from "@/components/common/ProfileImgAvatar.vue";
import FollowButton from "@/components/user/FollowButton.vue";
export default {
  components: { ProfileImgAvatar,FollowButton },
  name: "UserListItem",
  props: {
    user: Object,
  },
  data() {
    return {
      followValue: "팔로우",
    };
  },
  methods: {
    toDetail() {
      this.$router.push("/profile/"+this.user.no).catch(()=>{});
    }
  },
};
</script>

<style scoped>
#followBtn {
  display: table-cell;
  vertical-align: middle;
  width: 60px !important;
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

</style>
