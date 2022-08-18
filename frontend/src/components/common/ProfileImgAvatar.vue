<!--
작성자 : 박윤하
프로필 이미지 아바타
-->
<template>
  <v-list-item-avatar
    size="40"
  >
    <img
      :src="`${IMG_BASE_URL}/` + user_no"
      :alt="imgName"
      @error="replaceByDefault"
    />
  </v-list-item-avatar>
</template>

<script>
import defaultProfileSetter from "@/utils/defaultProfileSetter.js";
import { API_BASE_URL } from "@/config";

export default {
  name: "ProfileIconAvatar",
  methods: {
    replaceByDefault: defaultProfileSetter.replaceByDefault,
  },
  props: {
    user_no: Number,
    imgSrc: {
      type: String,
    },
    imgName: {
      type: String,
    },
  },
  data() {
    return {
      IMG_BASE_URL: API_BASE_URL + "/users/profile/image",
    };
  },
};
</script>

<style scoped>
img {
  object-fit: fill;
}
</style>
