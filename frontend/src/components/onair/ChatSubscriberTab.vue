<template>
  <div style="height: 80vh">
    <v-tabs
      v-model="tab"
      background-color="deep-purple accent-4"
      centered
      dark
      icons-and-text
      style="height:10vh"
    >
      <v-tabs-slider></v-tabs-slider>

      <v-tab href="#participants">
        참가자 목록
        <v-icon>mdi-account-box</v-icon>
      </v-tab>

      <v-tab href="#chat">
        채팅
        <v-icon>mdi-heart</v-icon>
      </v-tab>

    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item value="participants">
        <RightSubscriber />
      </v-tab-item>

      <v-tab-item value="chat">
        <RightChat />
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import RightChat from '@/components/onair/RightChat.vue'
import RightSubscriber from '@/components/onair/RightSubscriber.vue'

export default {
  name: 'ChatSubscriberTab',
  components: {
    RightChat,
    RightSubscriber,
  },
  data() {
    return {
      tab: 'participants'
    }
  }
}
</script>

<style>

</style>
