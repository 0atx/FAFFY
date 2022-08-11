<template>
  <div id="tab">
    <div class="mx-2">
      <right-subscriber @change="switchTab" v-if="tab==='participants'" />
      <right-chat @change="switchTab" v-else/>
    </div>
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
  position: sticky;
  top: 0;
  right: 0;
}
</style>
