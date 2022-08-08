<template>
<div v-if="streamManager" style="position:relative;">
	<ov-video :stream-manager="streamManager"/>
	<div class="nameTag"><p>{{ clientData }}</p>
    <button @click="capture">캡쳐</button></div>
    <canvas :id="`drawCanvas${clientData}`" width="320" height="240" style="border:1px solid black" :ref="{clientData}"/>
<!--
<div v-if="streamManager">
	<ov-video :stream-manager="streamManager" ref="ov_video"/>
	<div><p>{{ clientData }}</p></div>
  <button @click="capture">캡쳐</button>
  <canvas :id="`drawCanvas${clientData}`" width="320" height="240" style="border:1px solid black" :ref="{clientData}"/> -->

</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
    capture() {
      console.log("#######################"+this.clientData);
      var video = this.$refs.ov_video.$refs.video;
      console.log(video);
      // var video = document.querySelector('video');
      var canvas = document.getElementById("drawCanvas"+this.clientData);
      // var canvas = this.$refs.clientData;
      console.log(canvas);
			var context = canvas.getContext("2d");

			context.drawImage(video, 0, 0, 320, 240);
      console.log("#######################");
    }
	},
};
</script>

<style scoped>
div > p {
  margin: 0;
}

.nameTag {
  border-radius: 5px;
  padding: 4px 7px;
  background-color: #ff7451;
  color: white;
  font-size: 1vw;
  position: absolute;
  top: 5%;
  left: 5%;
}
</style>
