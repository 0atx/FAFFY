<template>
	<div id="topVideo"
    :class="{
      'webcam-container': true,

    }">
    상단 비디오 컴포넌트 레이아웃 짜야해 하~
    <!--
      'under-two': state.subscribers.length >= 1,
      'under-four': state.subscribers.length >= 2,
      'under-nine': state.subscribers.length >= 4,
    <user-video id="myWebcam"
      :stream-manager="state.publisher"/>
    <user-video
      v-for="sub in state.subscribers"
      :key="sub.stream.connection.connectionId"
      :stream-manager="sub"/>
    -->
  </div>
</template>

<script>
export default {
	name: 'TopVideo',
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
#topVideo {
  width:100%;
  height:100%;
  background-color: paleturquoise;
}

.webcam-container {
  display: grid;
  gap: 10px;
}

.under-two {
  grid-template-columns: 1fr 1fr;
}

.under-four {
  grid-template-columns: 1fr 1fr;
}

.under-nine {
  grid-template-columns: 1fr 1fr 1fr;
}
</style>
