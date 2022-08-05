<template v-slot:default>
  <div>
    <v-list-item :key="user.nickname">
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
          <!-- <div id="followBtn">
            <v-btn elevation="0" class="pt-1" icon small color="#ff7451">
              <v-icon> mdi-heart </v-icon>
            </v-btn>
          </div> -->
          <div id="followBtn">
            <dark-button class="mb-2" :btnValue="followValue" />
          </div>
          <!--<dark-button class="mb-2" :btnValue="followValue" />-->
        </v-list-item-content>
      </template>
    </v-list-item>
  </div>
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
