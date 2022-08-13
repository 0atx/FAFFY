<template>
	<div id="rightSub">
    <!--<div style="height: 10vh">
      <div id="host">
        <span>컨설턴트: {{ host.nickname }}</span>
      </div>
      <div id="participantCount">
        <div>참여 인원: {{ participants.length }} / {{ participantsLimit }}</div>
      </div>
    </div>
    <v-card class="overflow-auto" height="60vh">
      <v-card v-for="participant in participants" :key="participant.no" outlined>
        <span @click="moveProfile(participant.no)" style="cursor: pointer">{{ participant.nickname }}</span>
      </v-card>
    </v-card>-->
    <v-list>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6 pt-1">
            참여자 목록
            <v-btn
              id="tapBtn"
              class="mb-1" style="margin-left:45%; cursor: pointer"
              elevation="0"
              icon
              small
              color="gray"
              :ripple="false"
              @click="$emit('change')"
            >
              <v-icon> mdi-chat </v-icon>
            </v-btn>
          </v-list-item-title>
          <v-list-item-title>컨설턴트 : {{ host.nickname }}</v-list-item-title>
          <v-list-item-subtitle>참여 인원 : {{ participants.length+1 }} / {{ participantsLimit }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-divider></v-divider>
    <div class="overflow-auto" style="height: 80vh">
      <v-list>
        <v-list-item class="participant">
          <profile-img-avatar :user_no="loginUser.no" />
          <v-list-item-content @click="moveProfile(loginUser.no)">
            <v-list-item-title v-text="loginUser.nickname"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item
          class="participant"
          v-for="(participant,i) in participants"
          :key="i"
        >

        <profile-img-avatar :user_no="participant.no" />

        <v-list-item-content @click="moveProfile(participant.no)">
          <v-list-item-title v-text="participant.nickname"></v-list-item-title>
        </v-list-item-content>
        </v-list-item>
      </v-list>
    </div>
  </div>
</template>

<script>
import ProfileImgAvatar from "@/components/common/ProfileImgAvatar.vue";
import {mapState} from "vuex";

export default {
	name: 'rightSubscriber',
  computed:{
    ...mapState("authStore",["loginUser"]),
    ...mapState("consultingStore",["participants"]),
  },
  components: {
    ProfileImgAvatar
  },
  data() {
    return {
      // 임시 데이터
      host: { no:7, nickname: '박기범' },
      // participants: [
      //   { no: 1, nickname: '이준성' },
      //   { no: 2, nickname: '김명석' },
      //   { no: 3, nickname: '김수만' },
      //   { no: 4, nickname: '류경하' },
      //   { no: 5, nickname: '박윤하' },
      //   { no: 6, nickname: '이용우' },
      // ],
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

.participant {
  cursor: pointer;
}

.participant:hover {
  background-color: #f0f0f0;
}

::v-deep ::-webkit-scrollbar {
  width: 10px;
  background: #f0f0f0;
  border-radius: 10px;
}

::v-deep ::-webkit-scrollbar-thumb {
  background: #0c0f66;
  border-radius: 10px;
}

#tapBtn::before {
  background-color: transparent;
}

</style>
