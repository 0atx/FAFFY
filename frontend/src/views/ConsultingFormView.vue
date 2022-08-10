<template>
  <div id="view">
    <div style="display:flex;width:100%;">
      <div style="width: 80%; height: 80vh">
        <top-video />
        <center-option />
        <bottom-info />
      </div>
      <div style="width:20%;" id="drawer">
        <!-- v-if 탭으로 둘 중 하나 보였다 안 보였다? -->
        <!-- <right-subscriber />
        <right-chat /> -->
        <v-icon v-if="!drawer" @click="drawer=!drawer">mdi-arrow-left</v-icon>
        <chat-subscriber-tab v-if="drawer" @hideDrawer="hideDrawer"/>
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
  methods: {
    hideDrawer() {
      this.drawer = !this.drawer
    }
  }
}
</script>

<style scoped>
#view {
  background-color: white;
  padding: 2% 2%;
  text-align: left;
}
#drawer {
  position: fixed;
  top: 50;
  right: 0;
  /* border: 1px solid black; */
}
</style>
