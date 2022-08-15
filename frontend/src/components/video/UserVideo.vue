<template>
  <div>
    <div v-if="streamManager" style="position:relative;">
      <ov-video :stream-manager="streamManager" ref="ov_video"/>
      <div v-if="!clientData.nickname.includes('화면')" class="nameTag">
        <p>{{ clientData.nickname }}
          <button @click="mosaic">시동</button>

          <button @click="changeMosaicValue">캡쳐</button>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn @click="capture" x-small elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="ml-2"><v-icon color="#fff">mdi-camera</v-icon></v-btn>
            </template>
            <span>사진 촬영</span>
          </v-tooltip>
        </p>
      </div>
        <canvas id="canvas2"  style="width: 98%; max-width: 540px; border-radius: 5px;" />
        <canvas id="canvas_mo2" style="width: 98%; max-width: 540px; border-radius: 5px; display:none" />
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
import * as blazeface from "@tensorflow-models/blazeface"
import('@tensorflow/tfjs');

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
  data() {
    return {
      video:undefined,
      mosaicValue:false,
      frame:30,
			width: 320,
			height: 240,
    }
  },
  mounted() {

  },
	methods: {
    // Mosaic On / Off
		changeMosaicValue() {
			this.mosaicValue = !this.mosaicValue;
			if (this.mosaicValue) {
				console.log("Mosaic Off -> On");
				console.log("on");
			}
			else {
				console.log("Mosaic On -> Off");
				console.log("off");
			}
		},
		getConnectionData () {
      let namecode = JSON.parse(this.streamManager.stream.connection.data).clientData.split(':');
        let user = {
          no:Number(namecode[0]),
          nickname:namecode[1],
        }
        return user;
		},
    async mosaic() {
			// video,v,str
      this.video = this.$refs.ov_video.$refs.video;
      console.log(this.streamManager);
      console.log("비디오정보");
      console.log(this.video);
			this.model = await blazeface.load();
			// 프레임마다 얼굴감지
			setInterval(() => {
				this.detectFaces(this.video)}, parseInt(1000/this.frame));
			// this.detectFaces(model,video,v,str)

		},
    async detectFaces(video) {

			let canvas = document.getElementById("canvas2");
			let ctx = canvas.getContext("2d");
			let canvasm = document.getElementById("canvas_mo2");
			let ctxm = canvasm.getContext("2d");
			const prediction = await this.model.estimateFaces(video, false);
			if (this.mosaicValue) {
				// if (v.srcObject!=this.mosaicStream) {
				// v.srcObject = this.mosaicStream;
				// }

				// draw the video first
				ctx.drawImage(video, 0, 0, this.width, this.height);
				prediction.forEach((pred) => {

				ctxm.drawImage(video, pred.topLeft[0] + 10, pred.topLeft[1] - 20, pred.bottomRight[0] - pred.topLeft[0] - 20,
					pred.bottomRight[1] - pred.topLeft[1] + 60, 0, 0, 24, 16);
				ctx.drawImage(canvasm, 0, 0, 24, 16, pred.topLeft[0] + 10, pred.topLeft[1] - 20,
					pred.bottomRight[0] - pred.topLeft[0] - 20, pred.bottomRight[1] - pred.topLeft[1] + 60)

				this.pre_tl = pred.topLeft;
				this.pre_br = pred.bottomRight;
				});
				if (prediction.length==0) {
				ctxm.drawImage(video, this.pre_tl[0] + 10, this.pre_tl[1] - 20, this.pre_br[0] - this.pre_tl[0] - 20,
					this.pre_br[1] - this.pre_tl[1] + 60, 0, 0, 24, 16);
				this.ctx.drawImage(canvasm, 0, 0, 24, 16, this.pre_tl[0] + 10, this.pre_tl[1] - 20,
					this.pre_br[0] - this.pre_tl[0] - 20, this.pre_br[1] - this.pre_tl[1] + 60)
				}
			}
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
