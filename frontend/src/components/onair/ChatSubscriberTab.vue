<template>
  <div style="height: 80vh">
    <v-container>
      <div class="d-flex justify-space-between">
        <v-icon @click="hideDrawer">mdi-arrow-right</v-icon>
        <div>{{ tab==='participants'? '참여자 목록' : '채팅' }}</div>
        <v-icon @click="switchTab" style="cursor: pointer">{{ tab==='participants'? 'mdi-account-multiple' : 'mdi-chat'}}</v-icon>
      </div>
      <v-container>
        <right-subscriber v-if="tab==='participants'" />
        <right-chat v-else/>
      </v-container>
    </v-container>
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

<style>

</style>
