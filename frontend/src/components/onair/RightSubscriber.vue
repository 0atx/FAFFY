<template>
	<div id="rightSub" style="height: 70vh">
    <div style="height: 10vh">
      <div id="host">
        <span>컨설턴트: {{ host.nickname }}</span>
      </div>
      <div id="participantCount">
        <div>참여 인원: {{ participants.length }} / {{ participantsLimit }}</div>
      </div>
      <p>참여 리스트</p>
    </div>
    <v-card class="overflow-auto" height="60vh">
      <v-card v-for="participant in participants" :key="participant.no" outlined>
        <span @click="moveProfile(participant.no)" style="cursor: pointer">{{ participant.nickname }}</span>
      </v-card>
    </v-card>
  </div>
</template>

<script>
export default {
	name: 'rightSubscriber',
  components: {

  },
  data() {
    return {
      // 임시 데이터
      host: { no:7, nickname: '박기범' },
      participants: [
        { no: 1, nickname: '이준성' },
        { no: 2, nickname: '김명석' },
        { no: 3, nickname: '김수만' },
        { no: 4, nickname: '류경하' },
        { no: 5, nickname: '박윤하' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
        { no: 6, nickname: '이용우' },
      ],
      participantsLimit: 10,
    }
  },
  methods: {
    moveProfile(userNo) {
      let routeData = this.$router.resolve({ name: 'profile', params: { no: userNo }})
      window.open(routeData.href, '_blank')
    }
  }
};
</script>

<style scoped>
#rightSub {
  width:100%;
  height:100%;
  /* background-color: palegoldenrod; */
}
</style>
