<template>
  <div>
    <div style="position:relative;" v-if="streamManager">
      <ov-video :width="vwidth" :height="vheight" :stream-manager="streamManager" ref="ov_video" :style="`visibility:${visibility}; position:${orgPosition}`"/>
      <canvas :width="vwidth" :height="vheight" style="border-radius: 5px;" ref="canvas" :style="`display:${canvasDisplay}`"/>
      <canvas :width="vwidth" :height="vheight" style="border-radius: 5px; display:none" ref="canvasm" />
      <div v-if="!clientData.nickname.includes('화면')" class="nameTag" display="none">
        <p>{{ clientData.nickname }}
          <button @click="mosaic">시동</button>

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
    isHost() {
      return this.clientData.no == this.consultingInfo.consultant_no;
    },
    canvasWidth() {
      if (this.clientData.no == this.consultingInfo.consultant_no)
        return this.width;
      else
        return this.width2;
    },
    canvasHeight() {
      if (this.clientData.no == this.consultingInfo.consultant_no)
        return this.height;
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
    },
    vwidth() {
      let v;
      if (this.isHost)
        v = this.width;
      else
        v = this.width2;

      return v;
    },
    vheight() {
      let v;
      if (this.isHost)
        v = this.height;
      else
        v = this.height2;

      return v;
    },
	},
  data() {
    return {
      visibility:"visible",
      video:undefined,
      mosaicValue:false,
      frame:30,
			width: 450,
			height: 360,
      width2:300,
      height2:240,
      hmul:0.9,
      pmul:0.6,
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
      console.log(this.video.srcObject);
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
      console.log(this.video.width);
      console.log(this.video.height);

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
				this.ctx.drawImage(video, 0, 0, this.vwidth, this.vheight);
				prediction.forEach((pred) => {
          // 너비높이는 건드릴거 없음
          let mul = this.isHost? this.hmul : this.pmul;
          let w = pred.bottomRight[0]*mul - pred.topLeft[0]*mul;
          let h = pred.bottomRight[1]*mul - pred.topLeft[1]*mul;
          // video로부터 얻을때는 원래 사이즈로 취급
          this.ctxm.drawImage(video, pred.topLeft[0], pred.topLeft[1], w/mul,h/mul,
           0, 0, w/10,h/10);
          // canvas에 그릴때는 해당 비율에 맞게
          this.ctx.drawImage(this.canvasm, 0, 0, w/10, h/10, pred.topLeft[0]*mul, pred.topLeft[1]*mul,
          w, h)

          this.pre_tl = pred.topLeft;
          this.pre_br = pred.bottomRight;
				});
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
