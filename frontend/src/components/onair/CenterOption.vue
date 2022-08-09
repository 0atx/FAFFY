<template>
	<div id="centerOption">
    <v-btn elevation="0" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-microphone</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-microphone-off</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-video</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-video-off</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-camera</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-reflect-horizontal</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-image-multiple</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-monitor-share</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-monitor-off</v-icon></v-btn>
    <v-btn elevation="0" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff">mdi-close</v-icon></v-btn>
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
  width:60%;
  padding: 10px;
  margin: 0 20%;
  text-align: center;
  border-radius: 40px;
  background-color: rgb(200, 200, 200);
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
