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
            <v-btn id="icon" icon small elevation="0" :ripple="false" class="mb-1 pr-2" @click="$emit('hideDrawer')">
              <v-icon size="22">mdi-arrow-right</v-icon>
            </v-btn>
            참여자 목록
            <v-btn
              id="tapBtn"
              class="mb-1" style="margin-left:32%; cursor: pointer"
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
          <!-- <v-list-item-title>컨설턴트 : {{ host.nickname }}</v-list-item-title> -->
          <v-list-item-title>참여 인원 : {{ participants.length+1 }} / {{ consultingInfo.roomSize }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-divider></v-divider>
    <div class="overflow-auto" style="margin-top:2px; margin-bottom: 3px;">
      <v-list style="height: 86vh;">
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
    ...mapState("consultingStore",["participants","consultingInfo"]),
  },
  components: {
    ProfileImgAvatar
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
  background-color: white;
  border: 1px solid rgb(214, 214, 214);
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

#icon::before {
  background-color: transparent;
}

</style>
