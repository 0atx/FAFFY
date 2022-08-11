<template>
  <div id="view">
    <div style="display:flex;width:100%;">
      <div style="width: 80%;">
        <top-video />
        <center-option />
        <bottom-info />
      </div>
      <div style="width:20%;" id="drawer">
        <chat-subscriber-tab />
      </div>
    </div>
  </div>
</template>

<script>
import TopVideo from '@/components/onair/TopVideo.vue'
import CenterOption from '@/components/onair/CenterOption.vue'
import BottomInfo from '@/components/onair/BottomInfo.vue'
import ChatSubscriberTab from '@/components/onair/ChatSubscriberTab.vue'

export default {
  name: 'ConsultingFormView',
  components: {
    TopVideo,
    CenterOption,
    BottomInfo,
    ChatSubscriberTab
  },
  data() {
    return {
    }
  },
  methods: {
  }
}
</script>

<style scoped>
#view {
  background-color: white;
  text-align: left;
}

</style>
