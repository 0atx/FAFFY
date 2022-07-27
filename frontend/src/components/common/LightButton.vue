<!--
작성자 : 박윤하
밝은 버튼
-->
<template>
  <v-btn
    id="lightBtn"
    class="mt-2"
    block
    rounded
    elevation="0"
    type="submit"
  >{{ btnValue }}</v-btn>
</template>

<script>
export default {
    name: 'LightButton',
    props: {
      btnValue: {
        type: String
      }
    },
}
</script>

<style scoped>
#lightBtn {
  background-color: #ff7451;
  color: #fff;
}
</style>
