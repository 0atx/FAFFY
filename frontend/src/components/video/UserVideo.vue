<template>
  <div>
    <div v-if="streamManager" style="position:relative;">
      <ov-video :stream-manager="streamManager" ref="ov_video"/>
      <div v-if="!clientData.nickname.includes('화면')" class="nameTag">
        <p>{{ clientData.nickname }}
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn @click="capture" x-small elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="ml-2"><v-icon color="#fff">mdi-camera</v-icon></v-btn>
            </template>
            <span>사진 촬영</span>
          </v-tooltip>
        </p>
      </div>
        <!--
        <div>
          <canvas :id="`drawCanvas${clientData}`" width="320" height="240" style="border:1px solid black" :ref="{clientData}"/>
          <button @click="upload">전송</button>
        </div>
        -->
    </div>
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
			const user = this.getConnectionData();
      console.log("user: " + user.nickname)
			return user;
		},
	},

	methods: {
		getConnectionData () {
      let namecode = JSON.parse(this.streamManager.stream.connection.data).clientData.split(':');
        let user = {
          no:Number(namecode[0]),
          nickname:namecode[1],
        }
        return user;
		},
    capture() {
      var video = this.$refs.ov_video.$refs.video;
      this.$emit('capture-event',video);
    },
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

.v-btn::before {
  background-color: transparent;
}
</style>
