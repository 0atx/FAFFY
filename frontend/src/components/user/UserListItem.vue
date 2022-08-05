<template v-slot:default>
  <v-list style="padding: 0">
    <v-list-item :key="user.nickname" v-on:click="toDetail()">
      <template v-slot:default>
        <profile-img-avatar :user_no="user.no" />

        <v-list-item-content id="itemContent">
          <div id="item">
            <v-list-item-title
              class="mb-1"
              v-html="user.nickname"
            ></v-list-item-title>
            <v-list-item-subtitle
              class="mt-1"
              v-html="user.info"
            ></v-list-item-subtitle>
          </div>
          <div id="followBtn">
            <dark-button class="mb-2" :btnValue="followValue" />
          </div>
        </v-list-item-content>
      </template>
    </v-list-item>
    <v-divider
      :key="index"
      inset
    ></v-divider>
  </v-list>
</template>

<script>
import DarkButton from "@/components/common/DarkButton.vue";

import ProfileImgAvatar from "@/components/common/ProfileImgAvatar.vue";
export default {
  components: { ProfileImgAvatar, DarkButton },
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

button:before {
  background-color: transparent;
}
</style>
