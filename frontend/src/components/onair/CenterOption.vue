<template>
	<div id="centerOption">
    중간 여기 높이 작게 줘야 함 비디오 오디오 화면공유 음악재생 나가기 등 옵션 버튼 자리 > 캡쳐랑 앨범 버튼도 여기다 둘까? 전부 아이콘 버튼으로 할 지 일부는 텍스트로 할 지도 정해야 할 듯

    <div class="mafia-setting-btn"><v-icon>mdi-information</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-microphone</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-microphone-off</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-video</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-video-off</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-camera</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-camera-off</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-image-multiple</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-monitor-share</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-monitor-off</v-icon></div>
    <div class="mafia-setting-btn"><v-icon>mdi-close</v-icon></div>
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
  width:100%;
  background-color: palegoldenrod;
}

.setting-btns {
  display: flex;
  justify-content: center;
}

.mafia-setting-btn {
  display: inline-block;
  cursor: pointer;
}


</style>
