<template>
  <div>
    <div style="position:relative;" v-if="streamManager">
      <ov-video :stream-manager="streamManager" ref="ov_video" :style="`visibility:${visibility}; position:${orgPosition}`"/>
      <canvas :width="canvasWidth" :height="canvasHeight" style="border-radius: 5px;" ref="canvas" :style="`display:${canvasDisplay}`"/>
      <canvas :width="canvasWidth" :height="canvasHeight" style="border-radius: 5px; display:none;" ref="canvasm" />
      <div v-if="!clientData.nickname.includes('화면')" class="nameTag" display="none">
        <p>{{ clientData.nickname }}
          <button @click="mosaic">시동</button>

          <button @click="changeMosaicValue">작동</button>
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
import * as blazeface from "@tensorflow-models/blazeface"
import {mapState} from "vuex";
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
    ...mapState("consultingStore",["consultingInfo"]),
		clientData () {
			const user = this.getConnectionData();
      console.log("user: " + user.nickname)
			return user;
		},
    canvasWidth() {
      if (this.clientData.no == this.consultingInfo.consultant_no)
        return this.width;
      else
        return this.width2;
    },
    canvasHeight() {
      if (this.clientData.no == this.consultingInfo.consultant_no)
        return 350;
      else
        return this.height2
    },
    orgPosition() {
      if (this.mosaicValue)
        return "absolute";
        else
        return "relative";
    },
    canvasDisplay() {
      if (this.mosaicValue)
      return "inline";
      else
      return "none";
    }
	},
  data() {
    return {
      visibility:"visible",
      video:undefined,
      mosaicValue:false,
      frame:30,
			width: 500,
			height: 375,
      width2:331,
      height2:264.8,
      model:undefined,
      interval:undefined,
      canvas:undefined,
      ctx:undefined,
      canvasm:undefined,
      ctxm:undefined,

    }
  },
  mounted() {
    setTimeout(() => {
      this.video = this.$refs.ov_video.$refs.video;
      this.height = 375;
    }, 1000)
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
      console.log("mosaic 시동");
			// video,v,str
      this.video = this.$refs.ov_video.$refs.video;
      // console.log(this.streamManager);
      // console.log("비디오정보");
      // console.log(this.video);

      if (this.model==undefined) {
        console.log("model initializing");
        this.canvas = this.$refs.canvas;
        this.ctx = this.canvas.getContext("2d");
        this.canvasm = this.$refs.canvasm;
        this.ctxm = this.canvasm.getContext("2d");
        this.model = await blazeface.load();
      }
      this.mosaicValue = !this.mosaicValue;
      if (this.mosaicValue) {
                this.visibility="hidden";
        // 프레임마다 얼굴감지
        console.log("interval go");
        this.interval = setInterval(() => {
          this.detectFaces(this.video)}, parseInt(1000/this.frame));
        // this.detectFaces(model,video,v,str)
      } else {
        console.log("interval stop");
        this.visibility="visible";

        clearInterval(this.interval);
      }



		},
    async detectFaces(video) {


			const prediction = await this.model.estimateFaces(video, false);
			if (this.mosaicValue) {
				// if (v.srcObject!=this.mosaicStream) {
				// v.srcObject = this.mosaicStream;
				// }

				// draw the video first
				this.ctx.drawImage(video, 0, 0, 500, 375);
				prediction.forEach((pred) => {
				this.ctxm.drawImage(video, pred.topLeft[0] + 10, pred.topLeft[1] - 20, pred.bottomRight[0] - pred.topLeft[0] - 20,
					pred.bottomRight[1] - pred.topLeft[1] + 60, 0, 0, 24, 16);
				this.ctx.drawImage(this.canvasm, 0, 0, 24, 16, pred.topLeft[0] + 10, pred.topLeft[1] - 20,
					pred.bottomRight[0] - pred.topLeft[0] - 20, pred.bottomRight[1] - pred.topLeft[1] + 60)

				this.pre_tl = pred.topLeft;
				this.pre_br = pred.bottomRight;
				});
				if (prediction.length==0) {
				this.ctxm.drawImage(video, this.pre_tl[0] + 10, this.pre_tl[1] - 20, this.pre_br[0] - this.pre_tl[0] - 20,
					this.pre_br[1] - this.pre_tl[1] + 60, 0, 0, 24, 16);
				this.ctx.drawImage(this.canvasm, 0, 0, 24, 16, this.pre_tl[0] + 10, this.pre_tl[1] - 20,
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

.hide {
   position: absolute !important;
   top: -9999px !important;
   left: -9999px !important;
}
</style>
