<template>
  <div id="tab">
    <div>
      <right-subscriber @change="switchTab" @hideDrawer="$emit('hideDrawer')" v-if="tab==='participants'" />
      <right-chat :session="session" @change="switchTab" @hideDrawer="$emit('hideDrawer')" v-else/>
    </div>
  </div>
</template>

<script>
import RightChat from '@/components/onair/RightChat.vue'
import RightSubscriber from '@/components/onair/RightSubscriber.vue'

export default {
  name: 'ChatSubscriberTab',
  props:{
    session:Object
  },
  components: {
    RightChat,
    RightSubscriber,
  },
  data() {
    return {
      tab: 'participants'
    }
  },
  methods: {
    switchTab() {
      if (this.tab==='participants') {
        this.tab = 'subscribers'
      } else {
        this.tab = 'participants'
      }
    },
    hideDrawer() {
      this.$emit('hideDrawer')
    }
  }
}
</script>

<style scoped>
#tab {
  height:100vh;
  position: sticky;
  top: 0;
  right: 0;
}
</style>
