<template>
	<div id="centerOption" class="grey lighten-2">
    <div id="optionButton" class="grey lighten-1">
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-microphone</v-icon></v-btn>
        </template>
        <span>음소거 해제</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-microphone-off</v-icon></v-btn>
        </template>
      <span>음소거</span>
      </v-tooltip>

      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-video</v-icon></v-btn>
        </template>
        <span>비디오 시작</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-video-off</v-icon></v-btn>
        </template>
        <span>비디오 중지</span>
      </v-tooltip>


      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-camera</v-icon></v-btn>
        </template>
        <span>사진 촬영</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-image-multiple</v-icon></v-btn>
        </template>
        <span>앨범</span>
      </v-tooltip>

      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-reflect-horizontal</v-icon></v-btn>
        </template>
        <span>좌우 반전</span>
      </v-tooltip>

      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-blur</v-icon></v-btn>
        </template>
        <span>모자이크</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-blur-off</v-icon></v-btn>
        </template>
        <span>모자이크 해제</span>
      </v-tooltip>


      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-monitor-share</v-icon></v-btn>
        </template>
        <span>화면 공유 시작</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-monitor-off</v-icon></v-btn>
        </template>
        <span>화면 공유 해제</span>
      </v-tooltip>

      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-close</v-icon></v-btn>
        </template>
        <span>방송 나가기</span>
      </v-tooltip>
    <!--
    <div class="mafia-setting-btn" @click="clickGuide"><i class="el-icon-guide"></i></div>
    <div v-if="state.mafiaManager.onAudio" class="mafia-setting-btn" @click="muteAudio"><i class="el-icon-microphone"></i></div>
    <div v-if="!state.mafiaManager.onAudio" class="mafia-setting-btn" @click="muteAudio"><i class="el-icon-turn-off-microphone"></i></div>
    <div v-if="state.onVideo" class="mafia-setting-btn" @click="muteVideo"><i class="el-icon-video-camera"></i></div>
    <div v-if="!state.onVideo" class="mafia-setting-btn" @click="muteVideo"><i class="el-icon-video-pause"></i></div>
    <div v-if="state.onScreen" class="mafia-setting-btn" @click="shareScreen"><i class="el-icon-video-camera"></i></div>
    <div v-if="!state.onScreen" class="mafia-setting-btn" @click="shareScreen"><i class="el-icon-video-pause"></i></div>
    <div class="mafia-setting-btn" @click="leaveRoom"><i class="el-icon-close"></i></div>
    -->
    </div>
  </div>
</template>

<script>
export default {
	name: 'CenterOption',
  components: {

  },
  data() {
    return {

    }
  },
  methods: {
  }
};
</script>

<style scoped>
#centerOption {
  width:100%;
  padding: 10px;
  padding-bottom: 20px;
}

#optionButton {
  width:60%;
  height: 70px;
  margin: 0 20%;
  text-align: center;
  padding-top: 12px;
  border-radius: 40px;
}

.onButton {
  width:45px;
  height:45px;
  background-color:#0c0f66;
  margin: 0 10px;
}

.offButton {
  width:45px;
  height:45px;
  background-color:#ff7451;
  margin: 0 10px;
}
</style>
