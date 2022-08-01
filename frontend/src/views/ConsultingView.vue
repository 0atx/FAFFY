<template>
	<div id="main-container" class="container">
		<!-- 방송 들어가기 전-->
		<div id="join" v-if="!session">
			<div id="img-div"><img src="resources/images/openvidu_grey_bg_transp_cropped.png" /></div>
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>방송 들어가기 </h1>
				<div class="form-group">
					<p>
						<label>참가자명</label>
						<input v-model="myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>화상회의명</label>
						<input v-model="mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
					</p>
				</div>
			</div>
		</div>
		<!-- 방송 들어간 후-->
		<div id="session" v-if="session">
			<div id="session-header">
				<h1 id="session-title">{{ mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
			</div>
			<div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
				<button @click="onOffCam">캠껐다키기</button> / 
				<button @click="onOffAudio">오디오껐다키기</button>
			</div>
			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../components/video/UserVideo';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + "13.124.166.155" + ":8443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'ConsultingView',

	components: {
		UserVideo,
	},

	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

			mySessionId: 's' + Math.floor(Math.random() * 100),
			myUserName: 'p' + Math.floor(Math.random() * 100),

			
		}
	},

	methods: {
		joinSession () {
			// openVidu 객체 생성
			this.OV = new OpenVidu(); 

			// 세션 생성
			this.session = this.OV.initSession();

			// 세션 내에서 이벤트 발생시 할 작업들

			// 수신되는 모든 스트림들에 대해서
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

			// 끝낸 모든 스트림들에 대해서
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// 보든 비동기 예외에 대해서
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// 유효한 유저 토큰으로 세션에 연결

			// 'getToken' 은 서버측에서 수행해야할 작업을 시뮬레이션
			// 'token' 매개 변수는 사백엔드에서 검색하여 반환해야 함
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},
		// 캠 껐다 키기
		onOffCam() {
			if(this.publisher.publishVideo){
				console.log("꺼져있으니까 킨다");
				this.publisher.publishVideo(false);
				this.session.publish(this.publisher);
			}
			else{
				console.log("켜져있으니까 끈다");
				this.publisher.publishVideo(true);
				this.session.publish(this.publisher);
			}
		},
		// 오디오 껐다 키기
		onOffAudio() {
			if(this.publisher.publishAudio){
				console.log("꺼져있으니까 킨다");
				this.publisher.publishAudio(false);
			}
			else{
				console.log("켜져있으니까 끈다");
				this.publisher.publishAudio(true);
			}
		},
		screenShare(){

		},

		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token)) 
					.catch(error => reject(error.response));
			});
		},
	}
}
</script>
<!-- <template>
	<div id="">
    방송 진행 페이지 입니다.
	</div>
</template>

<script>
export default {
	name: "MainView",

	data() {
		return {};
	},
	mounted() {},

	methods: {},
};
</script>

<style scoped>

</style> -->
