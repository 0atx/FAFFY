<template>
  <div id="view">
    <v-btn color="success" @click="drawer = !drawer">{{ drawer? '숨기기' : '보이기' }}</v-btn>
    <div style="display:flex;width:100%;">
      <div style="width: 80%;">
        <top-video />
        <center-option />
        <bottom-info />
      </div>
      <div style="width:20%;" v-show="drawer">
        <!-- v-if 탭으로 둘 중 하나 보였다 안 보였다? -->
        <!-- <right-subscriber />
        <right-chat /> -->
        <v-navigation-drawer v-model="drawer" right>
          <chat-subscriber-tab />
        </v-navigation-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import TopVideo from '@/components/onair/TopVideo.vue'
import CenterOption from '@/components/onair/CenterOption.vue'
import BottomInfo from '@/components/onair/BottomInfo.vue'
// import RightSubscriber from '@/components/onair/RightSubscriber.vue'
// import RightChat from '@/components/onair/RightChat.vue'
import ChatSubscriberTab from '@/components/onair/ChatSubscriberTab.vue'

export default {
  name: 'ConsultingFormView',
  components: {
    TopVideo,
    CenterOption,
    BottomInfo,
    // RightSubscriber,
    // RightChat,
    ChatSubscriberTab
  },
  data() {
    return {
      drawer: true,
    }
  },
}
</script>

<style scoped>
#view {
  background-color: white;
  padding: 2% 2%;
  text-align: left;
}
</style>
