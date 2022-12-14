<template>
  <div>
    <div style="position:relative;" v-if="streamManager">
      <ov-video :width="vwidth" :height="vheight" :stream-manager="streamManager" ref="ov_video" :style="`visibility:${visibility}; position:${orgPosition}`"/>
      <canvas :width="vwidth" :height="vheight" style="border-radius: 5px;" ref="canvas" :style="`display:${canvasDisplay}`"/>
      <canvas :width="vwidth" :height="vheight" style="border-radius: 5px; display:none" ref="canvasm" />
      <div v-if="!clientData.nickname.includes('화면')" class="nameTag" display="none">
        <div style="display:none">안보이지롱{{mosaic}}</div>
        <p>{{ clientData.nickname }}
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn @click="capture" x-small elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="ml-2"><v-icon color="#fff">mdi-camera</v-icon></v-btn>
            </template>
            <span>사진 촬영</span>
          </v-tooltip>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import OvVideo from './OvVideo';
import * as blazeface from "@tensorflow-models/blazeface"
import * as tmImage from '@teachablemachine/image';

import {mapState,mapMutations} from "vuex";
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
    ...mapState("authStore",["loginUser"]),
    ...mapState("consultingStore",["consultingInfo","participants","myMosaic","remoteValue"]),
		clientData () {
			const user = this.getConnectionData();
			return user;
		},
    isHost() {
      return this.clientData.no == this.consultingInfo.consultant_no;
    },
    isMyVideo() {
      return this.clientData.no == this.loginUser.no;
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
    mosaic() {
      let v = false;
      if (this.isMyVideo) {
        v = this.myMosaic;
      } else {
        this.participants.forEach(element => {
          if (element.no == this.clientData.no) {
            v = element.mosaicValue;

          }
        });
      }
       this.changeMosaicValue(v);
      return v;
    }
	},
  data() {
    return {
      width: 450,
			height: 360,
      width2:300,
      height2:240,
      // 모자이크
      visibility:"visible",
      video:undefined,
      frame:30,
      mosaicValue:false,
      hmul:0.9,
      pmul:0.6,
      model:undefined,
      interval:undefined,
      interval2:undefined,

      canvas:undefined,
      ctx:undefined,
      canvasm:undefined,
      ctxm:undefined,

      // 모션 인식
      URL: "https://teachablemachine.withgoogle.com/models/85DlgcB-8/",
			modelURL: "https://teachablemachine.withgoogle.com/models/85DlgcB-8/model.json",
			metadataURL: "https://teachablemachine.withgoogle.com/models/85DlgcB-8/metadata.json",
			model2: undefined,
			captureSignal: false,

    }
  },
  mounted() {
    setTimeout(() => {
      this.mosaicStart();
      this.recognition();
    }, 2000)
  },
	methods: {
    ...mapMutations("consultingStore",["SET_REMOTE"]),
    // Mosaic On / Off
		changeMosaicValue(v) {
			this.mosaicValue = v;
			if (this.mosaicValue) {
        this.visibility="hidden";
        this.mosaicStart();
			}
			else {
        this.visibility="visible";
        this.mosaicStop();
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
    async mosaicStart() {
      this.video = this.$refs.ov_video.$refs.video;

      if (this.model==undefined) {
        this.canvas = this.$refs.canvas;
        this.ctx = this.canvas.getContext("2d");
        this.canvasm = this.$refs.canvasm;
        this.ctxm = this.canvasm.getContext("2d");
        this.model = await blazeface.load();
      }

        // 프레임마다 얼굴감지
        this.interval = setInterval(() => {
          this.detectFaces(this.video)}, parseInt(1000/this.frame));
		},
    async detectFaces(video) {
			if (this.mosaicValue) {
			const prediction = await this.model.estimateFaces(video, false);

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
    async mosaicStop() {
        clearInterval(this.interval);
    },

    capture() {
      var video = this.$refs.ov_video.$refs.video;

      if (this.mosaicValue)
        video = this.$refs.canvas;
      this.$emit('capture-event',video);
    },

    async recognition() {
			// video,v,str
      if (!this.isMyVideo)
        return;

			this.model2= await tmImage.load(this.modelURL, this.metadataURL);

			setInterval(() => {
				this.interval2 = this.motionRecognition(this.video)}, parseInt(1000/this.frame));

		},
    async motionRecognition(video) {
      if (!this.remoteValue)
        return;
			const prediction = await this.model2.predict(video, false);
			if (prediction[0].probability>0.98) {
        if (this.captureSignal == false) {
          this.$dialog.message.info('3초 후 스냅샷을 촬영합니다', {
            position: "top",
            timeout: 3000,
            color: "#ff7451",
          });
          clearInterval(this.interval2);
          this.SET_REMOTE(false);
          setTimeout(()=> {
            this.capture();
            this.captureSignal = false;
          },3000);
        }
				this.captureSignal=true;
			}


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
  top: 3%;
  left: 7%;
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
