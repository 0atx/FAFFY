<!--
작성자 : 박윤하
프로필 이미지 아바타
-->
<template>
  <v-avatar>
    <img
      :src=imgSrc
      :alt=imgName
    >
  </v-avatar>
</template>

<script>
export default {
  name: 'ProfileIconAvatar',
  props: {
    imgSrc: {
      type: String
    },
    imgName: {
      type: String
    }
  },
}
</script>

<style scoped>

</style>
