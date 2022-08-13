<template>
<div v-if="streamManager" style="position:relative;">
	<ov-video :stream-manager="streamManager" ref="ov_video"/>
    <div class="nameTag">
      <p>{{ clientData.nickname }}</p>
      <button @click="capture">캡쳐</button>
    </div>
    <!-- <div>
    <canvas :id="`drawCanvas${clientData}`" width="320" height="240" style="border:1px solid black" :ref="{clientData}"/>
    <button @click="upload">전송</button>
    </div> -->
</div>
</template>

<script>
import OvVideo from './OvVideo';
import {consulting} from '@/api/consulting.js'

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
			return user;
		},
	},

	methods: {
		getConnectionData () {
			// const { connection } = this.streamManager.stream;
			// return JSON.parse(connection.data);
      let namecode = JSON.parse(this.streamManager.stream.connection.data).clientData.split(':');
        let user = {
          no:Number(namecode[0]),
          nickname:namecode[1],
        }
        return user;
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
      console.log("캡쳐 완료");

      console.log("#######################");


    },
    upload() {
      console.log("#######################");

      var canvas = document.getElementById("drawCanvas"+this.clientData);
      const imgBase64 = canvas.toDataURL('image/jpeg','multipart/form-data');
			const decodImg = atob(imgBase64.split(',')[1]);

			let array = [];
			for (let i = 0; i < decodImg.length; i++) {
				array.push(decodImg.charCodeAt(i));
			}

			const file = new Blob([ new Uint8Array(array) ], {
				type : 'image/jpeg'
			});
			const fileName = 'web_snapshot_'+Date.now()+'.jpg';
			let formData = new FormData();
      const consulting_no = this.streamManager.session.sessionId;
			formData.append('file', file, fileName);
      formData.append('consulting_no',consulting_no);
      console.log(formData);

      consulting.uploadSnapshop(formData)
      .then(response=> {
        console.log(response);
      })
      .catch(response => {
        console.log(response);
      })

      console.log("#######################");


      // $.ajax({
			// 	type : 'post',
			// 	url : '../upload',
			// 	cache : false,
			// 	data : formData,
			// 	processData : false,
			// 	contentType : false,
			// 	success : function(data) {
			// 		alert('Uploaded !!');
			// 	}
			// });
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
  /* position: absolute; */
  top: 5%;
  left: 5%;
}
</style>
