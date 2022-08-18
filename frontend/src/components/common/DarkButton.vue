<!--
작성자 : 박윤하
어두운 버튼
-->
<template>
  <v-btn
    id="darkBtn"
    class="mt-2"
    block
    rounded
    elevation="0"
    type="submit"
  >{{ btnValue }}</v-btn>
</template>

<script>
export default {
    name: 'DarkButton',
    props: {
      btnValue: {
        type: String
      }
    },
}
</script>

<style scoped>
#darkBtn {
  background-color: #0c0f66;
  color: #fff;
}
</style>
